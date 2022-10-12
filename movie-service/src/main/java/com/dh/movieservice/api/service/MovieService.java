package com.dh.movieservice.api.service;

import com.dh.movieservice.Exceptions.ResourceNotFoundException;
import com.dh.movieservice.api.client.MovieCharacterServiceClient;
import com.dh.movieservice.domain.mapper.MovieMapper;
import com.dh.movieservice.domain.model.Genre;
import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.dto.CharacterShowDto;
import com.dh.movieservice.domain.model.dto.MovieCompleteDto;
import com.dh.movieservice.domain.model.dto.MovieShowDto;
import com.dh.movieservice.domain.repository.IGenreRepository;
import com.dh.movieservice.domain.repository.IMovieRepository;
import com.dh.movieservice.queue.MovieSender;
import com.dh.movieservice.shared.GenericServiceImpl;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService extends GenericServiceImpl<Movie, Long> implements IMovieService {

    private final IMovieRepository movieRepository;

    private final IGenreRepository genreRepository;

    private final MovieCharacterServiceClient movieCharacterServiceClient;

    private final MovieSender movieSender;

    private final MovieMapper movieMapper;

    @Override
    public Movie save(Movie movie) {
        Movie movieSave = new Movie();
        if (movie.getId() == null){
            movieSave = movieRepository.save(movie);
            log.info("Película registrada correctamente: "+ movieSave);
            movieSender.sendMovie(movieSave, "save");
        } else {
            movieSave = movieRepository.save(movie);
            log.info("Película actualizada correctamente: "+ movieSave);
            movieSender.sendMovie(movieSave, "save");
        }
        return movieSave;
    }

    @Override
    @CircuitBreaker(name="movieDetail", fallbackMethod = "movieDetailFallbackMethod")
    public MovieCompleteDto getById(Long id) throws ResourceNotFoundException {
        List<CharacterShowDto> characters = movieCharacterServiceClient.getCharactersByMovieId(id).getBody();
        Movie movie = movieMapper.validador(id);
        MovieCompleteDto movieCompleteDto = movieMapper.toMovieCompleteDto(movie, characters);
        log.info("Búsqueda exitosa: " + movieCompleteDto);
        return movieCompleteDto;
    }

    public MovieCompleteDto movieDetailFallbackMethod(CallNotPermittedException exception){
        return MovieCompleteDto.builder()
                .title("fallo")
                .build();
    }

    @Override
    public List<MovieShowDto> getAll() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieShowDto> movieShowDtos = new ArrayList<>();
        for(Movie movie : movies){
            movieShowDtos.add(movieMapper.toMovieShowDto(movie));
        }
        return movieShowDtos;
    }

    @Override
    public List<MovieShowDto> getOrder(String order) {
        List<MovieShowDto> movieShowDtos = new ArrayList<>();
        if("ASC".equals(order)){
            List<Movie> movies = movieRepository.getOrderAsc(order);
            for(Movie movie : movies) {
                movieShowDtos.add(movieMapper.toMovieShowDto(movie));
            }
        } else {
            List<Movie> movies = movieRepository.getOrderDesc(order);
            for(Movie movie : movies) {
                movieShowDtos.add(movieMapper.toMovieShowDto(movie));
            }
        }
        return movieShowDtos;
    }

    @Override
    public MovieShowDto getByTitle(String name) throws ResourceNotFoundException {
        Optional<Movie> movie = movieRepository.getByTitle(name);
        if (movie.isPresent()) {
            return movieMapper.toMovieShowDto(movie.get());
        } else {
            throw new ResourceNotFoundException("No se encontró Película con este título");
        }
    }

    @Override
    public List<MovieShowDto> getByGenre(Long genreId){
        List<MovieShowDto> movieShowDtos = new ArrayList<>();
        List<Movie> movies = movieRepository.getByGenre(genreId);
        for(Movie movie : movies) {
            movieShowDtos.add(movieMapper.toMovieShowDto(movie));
        }
        return movieShowDtos;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Movie movie = movieMapper.validador(id);
        movieRepository.deleteById(id);
        log.info("Se eliminó la Película correctamente: id("+id+")");
        movieSender.sendMovie(movie, "delete");
    }

    public Genre saveGenre(Genre genre) {
        Genre genreSave = new Genre();
        if (genre.getId() == null){
            genreSave = genreRepository.save(genre);
            log.info("Género registrado correctamente: "+ genreSave);
        } else {
            genreSave = genreRepository.save(genre);
            log.info("Género actualizado correctamente: "+ genreSave);
        }
        return genreSave;
    }

    @Override
    public JpaRepository<Movie, Long> getRepository() {
            return movieRepository;
    }
}
