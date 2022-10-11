package com.dh.characterservice.api.service;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;
import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.dto.CharacterCompleteDto;
import com.dh.characterservice.domain.model.dto.CharacterShowDto;
import com.dh.characterservice.shared.GenericServiceAPI;

import java.util.List;

public interface ICharacterService extends GenericServiceAPI<Character, Long> {

    CharacterCompleteDto getById(Long id) throws ResourceNotFoundException;
    CharacterShowDto getByName(String name) throws ResourceNotFoundException;
    List<CharacterShowDto> filter(Long movieId, Integer age, List<Double> weight);
}
