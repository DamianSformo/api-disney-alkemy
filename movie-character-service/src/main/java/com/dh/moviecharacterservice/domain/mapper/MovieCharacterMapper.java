package com.dh.moviecharacterservice.domain.mapper;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import com.dh.moviecharacterservice.domain.repository.IMovieCharacterRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class MovieCharacterMapper implements IValidador<MovieCharacter>{

    @Autowired
    IMovieCharacterRepository movieCharacterRepository;

    @BeforeMapping
    public MovieCharacter validador(Long id) throws ResourceNotFoundException {
        Optional<MovieCharacter> movieCharacter = movieCharacterRepository.findById(id);
        if (movieCharacter.isEmpty()) {
            throw new ResourceNotFoundException(msjError + id);
        }
        return movieCharacter.get();
    }

}
