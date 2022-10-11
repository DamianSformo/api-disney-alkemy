package com.dh.moviecharacterservice.api.controller;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;
import com.dh.moviecharacterservice.api.service.MovieCharacterServiceImpl;
import com.dh.moviecharacterservice.domain.model.MovieCharacter;
import com.dh.moviecharacterservice.domain.model.dto.CharacterShowDto;
import com.dh.moviecharacterservice.domain.model.dto.MovieShowDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
public class MovieCharacterController {

    private final MovieCharacterServiceImpl movieCharacterService;

    public MovieCharacterController(MovieCharacterServiceImpl movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    //* ///////// POST ///////// *//

    @Operation(summary = "Guardar o actualizar una Película-Personaje")
    @PostMapping("")
    public ResponseEntity<MovieCharacter> save(@RequestBody MovieCharacter movieCharacter) {
        if(movieCharacter.getId() == null)
            return ResponseEntity.status(HttpStatus.CREATED).body(movieCharacterService.save(movieCharacter));
        else
            return ResponseEntity.ok(movieCharacterService.save(movieCharacter));
    }

    //* ///////// GET ///////// *//

    @Operation(summary = "Traer Personajes por Película")
    @GetMapping("/characters/{movieId}")
    public ResponseEntity<List<CharacterShowDto>> getCharactersByMovieId(@PathVariable Long movieId){
        return ResponseEntity.ok(movieCharacterService.getCharactersByMovieId(movieId));
    }

    @Operation(summary = "Traer Películas por Personaje")
    @GetMapping("/movies/{characterId}")
    public ResponseEntity<List<MovieShowDto>> getMoviesByCharacterId(@PathVariable Long characterId){
        return ResponseEntity.ok(movieCharacterService.getMoviesByIdCharacter(characterId));
    }

    //* ///////// DELETE ///////// *//

    @Operation(summary = "Eliminar Película-Personaje por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException {
        movieCharacterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
