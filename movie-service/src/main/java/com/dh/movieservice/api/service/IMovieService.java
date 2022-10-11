package com.dh.movieservice.api.service;

import com.dh.movieservice.Exceptions.ResourceNotFoundException;
import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.dto.MovieCompleteDto;
import com.dh.movieservice.domain.model.dto.MovieShowDto;
import com.dh.movieservice.shared.GenericServiceAPI;

import java.util.List;

public interface IMovieService extends GenericServiceAPI<Movie, Long> {

    MovieCompleteDto getById(Long id) throws ResourceNotFoundException;
    List<MovieShowDto> getAll();
    List<MovieShowDto> getOrder(String order);
    List<MovieShowDto> getByGenre(Long genreId);
    MovieShowDto getByTitle(String title) throws ResourceNotFoundException;
}
