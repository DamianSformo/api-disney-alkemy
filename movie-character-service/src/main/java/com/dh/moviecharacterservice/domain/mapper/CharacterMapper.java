package com.dh.moviecharacterservice.domain.mapper;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.model.dto.CharacterShowDto;
import com.dh.moviecharacterservice.domain.repository.ICharacterRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CharacterMapper implements IValidador<Character> {

    @Autowired
    ICharacterRepository characterRepository;

    @BeforeMapping
    public Character validador(Long id) throws ResourceNotFoundException {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isEmpty()) {
            throw new ResourceNotFoundException(msjError + id);
        }
        return character.get();
    }

    public CharacterShowDto toCharacterShowDto(Character character){
        return CharacterShowDto.builder()
                .id(character.getId())
                .image(character.getImage())
                .name(character.getName())
                .build();
    }
}

