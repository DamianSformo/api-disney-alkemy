package com.dh.movieservice.domain.data;

import com.dh.movieservice.domain.model.Genre;
import com.dh.movieservice.domain.repository.IGenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final IGenreRepository genreRepository;

    public DataLoader(IGenreRepository genreRepository) {

        this.genreRepository = genreRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        var genre1 = new Genre();
        genre1.setImage("url.terror");
        genre1.setName("terror");

        var gDB1 = genreRepository.save(genre1);
        log.info(gDB1.toString());



        /*

        var movieCharacter1 = new MovieCharacter();
        movieCharacter1.setId(1L);
        movieCharacter1.setMovie(mDB1.getBody());
        movieCharacter1.setCharacter(cDB1.getBody());

        var mcDB1 = movieCharacterService.save(movieCharacter1);
        log.info(mcDB1.toString());

        var movieCharacter2 = new MovieCharacter();
        movieCharacter2.setId(2L);
        movieCharacter2.setMovie(mDB1.getBody());
        movieCharacter2.setCharacter(cDB2.getBody());

        var mcDB2 = movieCharacterService.save(movieCharacter2);
        log.info(mcDB2.toString());



        var movieCharacter3 = new MovieCharacter();
        movieCharacter3.setId(3L);
        movieCharacter3.setMovie(mDB1.getBody());
        movieCharacter3.setCharacter(cDB3.getBody());

        var mcDB3 = movieCharacterService.save(movieCharacter3);
        log.info(mcDB3.toString());



        var movieCharacter4 = new MovieCharacter();
        movieCharacter4.setId(4L);
        movieCharacter4.setMovie(mDB2.getBody());
        movieCharacter4.setCharacter(cDB3.getBody());

        var mcDB4 = movieCharacterService.save(movieCharacter4);
        log.info(mcDB4.toString());

        */
    }
}
