package com.dh.characterservice.api.service;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;
import com.dh.characterservice.api.client.MovieCharacterServiceClient;
import com.dh.characterservice.domain.mapper.CharacterMapper;
import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.dto.CharacterCompleteDto;
import com.dh.characterservice.domain.model.dto.CharacterShowDto;
import com.dh.characterservice.domain.model.dto.MovieShowDto;
import com.dh.characterservice.domain.repository.ICharacterRepository;
import com.dh.characterservice.queue.CharacterSender;
import com.dh.characterservice.shared.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterServiceImpl extends GenericServiceImpl<Character, Long> implements ICharacterService {

    private final ICharacterRepository characterRepository;

    private final MovieCharacterServiceClient movieCharacterServiceClient;

    private final CharacterSender characterSender;

    private final CharacterMapper characterMapper;

    @Override
    public Character save(Character character) {
        Character characterSave = new Character();
        if (character.getId() == null){
            characterSave = characterRepository.save(character);
            log.info("Personaje registrado correctamente: "+ characterSave);
            characterSender.sendCharacter(characterSave, "save");
        } else {
            characterSave = characterRepository.save(character);
            log.info("Personaje actualizado correctamente: "+ characterSave);
            characterSender.sendCharacter(characterSave, "save");
        }
        return characterSave;
    }

    @Override
    @CircuitBreaker(name="characterDetail", fallbackMethod = "characterDetailFallbackMethod")
    public CharacterCompleteDto getById(Long id) throws ResourceNotFoundException {
        List<MovieShowDto> movies = movieCharacterServiceClient.getMoviesByCharacterId(id).getBody();
        Character character = characterMapper.validador(id);
        CharacterCompleteDto characterCompleteDto = characterMapper.toCharacterCompleteDto(character, movies);
        log.info("Búsqueda exitosa: " + characterCompleteDto);
        return characterCompleteDto;
    }

    public CharacterCompleteDto characterDetailFallbackMethod(CallNotPermittedException exception){
        return CharacterCompleteDto.builder()
                .name("fallo")
                .build();
    }

    @Override
    public List<CharacterShowDto> getAll() {
        List<Character> characters = characterRepository.findAll();
        List<CharacterShowDto> characterShowDtos = new ArrayList<>();
        for(Character character : characters){
            characterShowDtos.add(characterMapper.toCharacterShowDto(character));
        }
        return characterShowDtos;
    }

    @Override
    public CharacterShowDto getByName(String name) throws ResourceNotFoundException {
        Optional<Character> character = characterRepository.getByName(name);
        if (character.isPresent()) {
            return characterMapper.toCharacterShowDto(character.get());
        } else {
            throw new ResourceNotFoundException("No se encontró Personaje con este nombre");
        }
    }

    @Override
    public List<CharacterShowDto> filter(Long movieId, Integer age, List<Double> weight) {
        List<Character> characters = new ArrayList<>();
        List<CharacterShowDto> characterShowDtosByMovie = new ArrayList<>();
        List<CharacterShowDto> characterShowDtosFilter = new ArrayList<>();

        List<CharacterShowDto> characterShowDtos = new ArrayList<>();

        if((movieId != null) && (age == null && weight == null)) {
            characterShowDtosByMovie = movieCharacterServiceClient.getCharactersByMovieId(movieId).getBody();
            characterShowDtos = characterShowDtosByMovie;
        }

        if((movieId == null) && (age != null || weight != null)) {
            if (age != null && weight != null){
                characters = characterRepository.filter(age, weight.get(0), weight.get(1));
            } else if (age != null) {
                characters = characterRepository.getByAge(age);
            } else if (weight != null){
                characters = characterRepository.getByWeight(weight.get(0), weight.get(1));
            }

            for(Character character : characters){
                characterShowDtosFilter.add(characterMapper.toCharacterShowDto(character));
            }

            characterShowDtos = characterShowDtosFilter;
        }

        if((movieId != null) && (age != null || weight != null)) {
            characterShowDtosByMovie = movieCharacterServiceClient.getCharactersByMovieId(movieId).getBody();
            if (age != null && weight != null){
                characters = characterRepository.filter(age, weight.get(0), weight.get(1));
            } else if (age != null) {
                characters = characterRepository.getByAge(age);
            } else if (weight != null){
                characters = characterRepository.getByWeight(weight.get(0), weight.get(1));
            }

            for(Character character : characters){
                characterShowDtosFilter.add(characterMapper.toCharacterShowDto(character));
            }

            if(characterShowDtosFilter.size() >= characterShowDtosByMovie.size()){
                for(int i = 0; i < characterShowDtosFilter.size(); i++){
                    if(characterShowDtosByMovie.contains(characterShowDtosFilter.get(i))){
                        characterShowDtos.add(characterShowDtosFilter.get(i));
                    };
                }
            } else {
                for(int i = 0; i < characterShowDtosByMovie.size(); i++){
                    if(characterShowDtosFilter.contains(characterShowDtosByMovie.get(i))){
                        characterShowDtos.add(characterShowDtosByMovie.get(i));
                    };
                }
            }
        }
        if(movieId == null && age == null && weight == null) {
            characters = characterRepository.findAll();
            for (Character character : characters) {
                characterShowDtos.add(characterMapper.toCharacterShowDto(character));
            }
        }
        return characterShowDtos;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Character character = characterMapper.validador(id);
        characterRepository.deleteById(id);
        log.info("Se eliminó el Personaje correctamente: id("+id+")");
        characterSender.sendCharacter(character, "delete");
    }

    @Override
    public JpaRepository<Character, Long> getRepository() {
        return characterRepository;
    }
}
