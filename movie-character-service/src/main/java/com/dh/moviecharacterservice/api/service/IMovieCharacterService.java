package com.dh.moviecharacterservice.api.service;

import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import com.dh.moviecharacterservice.domain.model.dto.CharacterShowDto;
import com.dh.moviecharacterservice.domain.model.dto.MovieShowDto;
import com.dh.moviecharacterservice.shared.GenericServiceAPI;

import java.util.List;

public interface IMovieCharacterService extends GenericServiceAPI<MovieCharacter, Long> {

    List<CharacterShowDto> getCharactersByMovieId(Long movieId);
    List<MovieShowDto> getMoviesByIdCharacter(Long idCharacter);

}
