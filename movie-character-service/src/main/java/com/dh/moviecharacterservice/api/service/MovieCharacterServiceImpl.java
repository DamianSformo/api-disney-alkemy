package com.dh.moviecharacterservice.api.service;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.mapper.CharacterMapper;
import com.dh.moviecharacterservice.domain.mapper.MovieCharacterMapper;
import com.dh.moviecharacterservice.domain.mapper.MovieMapper;
import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.model.Movie;
import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import com.dh.moviecharacterservice.domain.model.dto.CharacterShowDto;
import com.dh.moviecharacterservice.domain.model.dto.MovieShowDto;
import com.dh.moviecharacterservice.domain.repository.IMovieCharacterRepository;
import com.dh.moviecharacterservice.shared.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl extends GenericServiceImpl<MovieCharacter, Long> implements IMovieCharacterService {

    private final IMovieCharacterRepository movieCharacterRepository;

    private final MovieCharacterMapper movieCharacterMapper;

    private final MovieMapper movieMapper;

    private final CharacterMapper characterMapper;

    @Override
    public MovieCharacter save(MovieCharacter movieCharacter) {
        MovieCharacter movieCharacterSave = new MovieCharacter();
        if (movieCharacter.getId() == null){
            movieCharacterSave = movieCharacterRepository.save(movieCharacter);
            log.info("Película-Personaje registrado correctamente: "+ movieCharacterSave);
        } else {
            movieCharacterSave = movieCharacterRepository.save(movieCharacter);
            log.info("Película-Personaje registrado correctamente: "+ movieCharacterSave);
        }
        return movieCharacterSave;
    }

    @Override
    public List<CharacterShowDto> getCharactersByMovieId(Long movieId) {
        List<Character> characters = movieCharacterRepository.getCharactersByMovieId(movieId);
        List<CharacterShowDto> characterShowDtos = new ArrayList<>();
        for(Character character : characters){
            characterShowDtos.add(characterMapper.toCharacterShowDto(character));
        }
        return characterShowDtos;
    }

    @Override
    public List<MovieShowDto> getMoviesByIdCharacter(Long idCharacter) {
        List<Movie> movies = movieCharacterRepository.getMoviesByCharacterId(idCharacter);
        List<MovieShowDto> movieShowDtos = new ArrayList<>();
        for(Movie movie : movies){
            movieShowDtos.add(movieMapper.toMovieShowDto(movie));
        }
        return movieShowDtos;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        movieCharacterMapper.validador(id);
        movieCharacterRepository.deleteById(id);
        log.info("Se eliminó la Película-Personaje correctamente: id("+id+")");
    }

    @Override
    public JpaRepository<MovieCharacter, Long> getRepository() {
        return movieCharacterRepository;
    }
}
