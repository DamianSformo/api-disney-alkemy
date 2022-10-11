package com.dh.moviecharacterservice.domain.repository;

import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.model.Movie;
import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {

    @Query("SELECT m.character FROM MovieCharacter m WHERE m.movie.id= ?1")
    List<Character> getCharactersByMovieId(Long movieId);

    @Query("SELECT m.movie FROM MovieCharacter m WHERE m.character.id= ?1")
    List<Movie> getMoviesByCharacterId(Long idCharacter);

}
