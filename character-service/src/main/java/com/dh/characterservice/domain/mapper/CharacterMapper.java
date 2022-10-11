package com.dh.characterservice.domain.mapper;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;

import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.dto.CharacterCompleteDto;
import com.dh.characterservice.domain.model.dto.CharacterShowDto;
import com.dh.characterservice.domain.model.dto.MovieShowDto;
import com.dh.characterservice.domain.repository.ICharacterRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    public CharacterCompleteDto toCharacterCompleteDto(Character character, List<MovieShowDto> movies){
        return CharacterCompleteDto.builder()
                .id(character.getId())
                .image(character.getImage())
                .name(character.getName())
                .age(character.getAge())
                .weight(character.getWeight())
                .history(character.getHistory())
                .movies(movies)
                .build();
    }

    public CharacterShowDto toCharacterShowDto(Character character){
        return CharacterShowDto.builder()
                .id(character.getId())
                .image(character.getImage())
                .name(character.getName())
                .build();
    }
}

