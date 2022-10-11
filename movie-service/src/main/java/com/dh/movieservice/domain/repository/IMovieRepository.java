package com.dh.movieservice.domain.repository;

import com.dh.movieservice.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.title = ?1")
    Optional<Movie> getByTitle(String name);

    @Query("SELECT m FROM Movie m WHERE m.genre.id = ?1")
    List<Movie> getByGenre(Long genreId);

    @Query("SELECT m FROM Movie m ORDER BY dateCreated ASC")
    List<Movie> getOrderAsc(String order);

    @Query("SELECT m FROM Movie m ORDER BY dateCreated DESC")
    List<Movie> getOrderDesc(String order);

}
