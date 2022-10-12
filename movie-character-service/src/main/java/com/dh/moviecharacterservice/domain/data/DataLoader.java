package com.dh.moviecharacterservice.domain.data;

import com.dh.moviecharacterservice.api.client.CharacterServiceClient;
import com.dh.moviecharacterservice.api.client.MovieServiceClient;
import com.dh.moviecharacterservice.api.service.ICharacterService;
import com.dh.moviecharacterservice.api.service.IMovieCharacterService;
import com.dh.moviecharacterservice.api.service.IMovieService;
import com.dh.moviecharacterservice.api.service.MovieCharacterServiceImpl;
import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.model.Genre;
import com.dh.moviecharacterservice.domain.model.Movie;
import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import com.dh.moviecharacterservice.domain.repository.IGenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final MovieCharacterServiceImpl movieCharacterService;
    private final MovieServiceClient movieServiceClient;
    private final CharacterServiceClient characterServiceClient;
    private final IGenreRepository genreRepository;

    public DataLoader(MovieCharacterServiceImpl movieCharacterService, MovieServiceClient movieServiceClient, CharacterServiceClient characterServiceClient, IGenreRepository genreRepository) {

        this.movieCharacterService = movieCharacterService;
        this.characterServiceClient = characterServiceClient;
        this.movieServiceClient = movieServiceClient;
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var genre1 = new Genre();
        genre1.setId(1L);
        genre1.setImage("url.terror");
        genre1.setName("terror");

        var gDB1 = genreRepository.save(genre1);
        var gcDB1 = movieServiceClient.saveGenre(genre1);
        log.info(gDB1.toString());

        var genre2 = new Genre();
        genre2.setId(2L);
        genre2.setImage("url.suspense");
        genre2.setName("suspense");

        var gDB2 = genreRepository.save(genre2);
        var gcDB2 = movieServiceClient.saveGenre(genre2);
        log.info(gDB2.toString());

        var movie1 = new Movie();
        movie1.setId(1L);
        movie1.setImage("url.candyman");
        movie1.setTitle("Candyman");
        movie1.setRating(4);
        movie1.setGenre(gDB1);
        Date date1 = new Date(121, 11, 11);
        movie1.setDateCreated(date1);

        var mDB1 = movieServiceClient.save(movie1);
        log.info(mDB1.toString());

        var movie2 = new Movie();
        movie2.setId(2L);
        movie2.setImage("url.sky-sharks");
        movie2.setTitle("Sky Sharks");
        movie2.setRating(2);
        movie2.setGenre(gDB1);
        Date date2 = new Date(122, 12, 12);
        movie2.setDateCreated(date2);

        var mDB2 = movieServiceClient.save(movie2);
        log.info(mDB2.toString());

        var character1 = new Character();
        character1.setId(1L);
        character1.setImage("url.jordan-peele");
        character1.setName("Jordan Peele");
        character1.setAge(43);
        character1.setWeight(87.8);
        character1.setHistory("Peele's breakout role came in 2003, when he was hired as a cast member on the Fox sketch comedy series Mad TV, where he spent five seasons, leaving the show in 2008. In the following years, he and his frequent Mad TV collaborator, Keegan-Michael Key, created and starred in their own Comedy Central sketch comedy series Key & Peele (2012–2015). In 2014, they appeared together as FBI agents in the first season of FX's anthology series Fargo.[3] Peele co-created the TBS comedy series The Last O.G. (2018–2022) and the YouTube Premium comedy series Weird City (2019). He has also served as the host and producer of the CBS All Access revival of the anthology series The Twilight Zone (2019–2020).");

        var cDB1 = characterServiceClient.save(character1);
        log.info(cDB1.toString());

        var character2 = new Character();
        character2.setId(2L);
        character2.setImage("url.vanessa-williams");
        character2.setName("Vanessa Williams");
        character2.setAge(37);
        character2.setWeight(54.3);
        character2.setHistory("Vanessa Estelle Williams (sometimes professionally credited as Vanessa A. Williams)[1] is an American actress and producer. She is best known for her roles as Maxine Joseph–Chadway in the Showtime drama series, Soul Food (2000–04), for which she received NAACP Image Award for Outstanding Actress in a Drama Series and as Nino Brown's feisty gun moll, Keisha in the 1991 crime drama film, New Jack City. Williams is also known for her role as Anne-Marie McCoy in the first and fourth of the Candyman films, and as Rhonda Blair in the first season of the Fox prime time soap opera, Melrose Place (1992–93).");

        var cDB2 = characterServiceClient.save(character2);
        log.info(cDB2.toString());

        var character3 = new Character();
        character3.setId(3L);
        character3.setImage("url.tony-todd");
        character3.setName("Tony Todd");
        character3.setAge(67);
        character3.setWeight(91.2);
        character3.setHistory("Tony Todd (born December 4, 1954) is an American actor who made his debut as Sgt. Warren in the film Platoon (1986), and portrayed Kurn in the television series Star Trek: The Next Generation (1990–1991) and Star Trek: Deep Space Nine (1996). He achieved stardom for his roles as Ben in the 1990 remake of Night of the Living Dead, as the titular character in the four films of the Candyman film series (1992–2021) and William Bludworth in the Final Destination franchise (2000–2011). He also starred as Dan in The Man from Earth (2007) and voiced The Fallen in Transformers: Revenge of the Fallen (2009), Darkseid in the DC Animated Movie Universe, Zoom in The Flash and Venom in the upcoming Spider-Man 2 (2023) game.");

        var cDB3 = characterServiceClient.save(character3);
        log.info(cDB3.toString());

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
    }
}
