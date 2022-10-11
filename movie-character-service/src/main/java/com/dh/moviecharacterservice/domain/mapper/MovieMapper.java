package com.dh.moviecharacterservice.domain.mapper;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.model.Movie;
import com.dh.moviecharacterservice.domain.model.dto.MovieShowDto;
import com.dh.moviecharacterservice.domain.repository.IMovieRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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

    public MovieShowDto toMovieShowDto(Movie movie){
        return MovieShowDto.builder()
                .id(movie.getId())
                .image(movie.getImage())
                .title(movie.getTitle())
                .dateCreated(movie.getDateCreated().toString())
                .build();
    }
}