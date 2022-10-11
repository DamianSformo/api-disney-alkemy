package com.dh.moviecharacterservice.api.service;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.mapper.CharacterMapper;
import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.repository.ICharacterRepository;
import com.dh.moviecharacterservice.shared.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterServiceImpl extends GenericServiceImpl<Character, Long> implements ICharacterService {

    private final ICharacterRepository characterRepository;

    private final CharacterMapper characterMapper;

    @Override
    public Character save(Character character) {
        Character characterSave = new Character();
        if (character.getId() == null){
            characterSave = characterRepository.save(character);
            log.info("Personaje registrado correctamente: "+ characterSave);
        } else {
            characterSave = characterRepository.save(character);
            log.info("Personaje actualizado correctamente: "+ characterSave);
        }
        return characterSave;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Character character = characterMapper.validador(id);
        characterRepository.deleteById(id);
        log.info("Se eliminó el Personaje correctamente: id("+id+")");

    }

    @Override
    public JpaRepository<Character, Long> getRepository() {
        return characterRepository;
    }
}
