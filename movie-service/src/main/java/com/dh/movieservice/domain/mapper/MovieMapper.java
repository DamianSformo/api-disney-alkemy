package com.dh.movieservice.domain.mapper;

import com.dh.movieservice.Exceptions.ResourceNotFoundException;
import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.dto.CharacterShowDto;
import com.dh.movieservice.domain.model.dto.MovieCompleteDto;
import com.dh.movieservice.domain.model.dto.MovieShowDto;
import com.dh.movieservice.domain.repository.IMovieRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class MovieMapper implements IValidador<Movie> {

    @Autowired
    IMovieRepository movieRepository;

    @BeforeMapping
    public Movie validador(Long id) throws ResourceNotFoundException {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new ResourceNotFoundException(msjError + id);
        }
        return movie.get();
    }

    public MovieCompleteDto toMovieCompleteDto(Movie movie, List<CharacterShowDto> characters){
        return MovieCompleteDto.builder()
                .id(movie.getId())
                .image(movie.getImage())
                .title(movie.getTitle())
                .dateCreated(movie.getDateCreated().toString())
                .rating(movie.getRating())
                .characters(characters)
                .genre(movie.getGenre().getName())
                .build();
    }

    public MovieShowDto toMovieShowDto(Movie movie){
        return MovieShowDto.builder()
                .id(movie.getId())
                .image(movie.getImage())
                .title(movie.getTitle())
                .dateCreated(movie.getDateCreated().toString())
                .build();
    }
}