package com.dh.moviecharacterservice.api.service;


import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.domain.mapper.MovieMapper;
import com.dh.moviecharacterservice.domain.model.Movie;
import com.dh.moviecharacterservice.domain.repository.IMovieRepository;
import com.dh.moviecharacterservice.shared.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl extends GenericServiceImpl<Movie, Long> implements IMovieService {

    private final IMovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Override
    public Movie save(Movie movie) {
        Movie movieSave = new Movie();
        if (movie.getId() == null){
            movieSave = movieRepository.save(movie);
            log.info("Película registrada correctamente: "+ movieSave);
        } else {
            movieSave = movieRepository.save(movie);
            log.info("Película actualizada correctamente: "+ movieSave);
        }
        return movieSave;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Movie movie = movieMapper.validador(id);
        movieRepository.deleteById(id);
        log.info("Se eliminó la Película correctamente: id("+id+")");
    }

    @Override
    public JpaRepository<Movie, Long> getRepository() {
        return movieRepository;
    }
}

