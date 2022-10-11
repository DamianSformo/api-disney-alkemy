package com.dh.moviecharacterservice.domain.repository;

import com.dh.moviecharacterservice.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {

}
