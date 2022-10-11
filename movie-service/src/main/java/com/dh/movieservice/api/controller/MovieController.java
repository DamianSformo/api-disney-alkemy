package com.dh.movieservice.api.controller;

import com.dh.movieservice.Exceptions.ResourceNotFoundException;
import com.dh.movieservice.api.service.MovieService;
import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.dto.MovieCompleteDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //* ///////// POST ///////// *//

    @Operation(summary = "Guardar o actualizar una Película")
    @PostMapping("")
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        if(movie .getId() == null)
            return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movie));
        else
            return ResponseEntity.ok(movieService.save(movie));
    }

    //* ///////// GET ///////// *//

    @Operation(summary = "Traer una Película Completa por Id")
    @GetMapping("/detail/{id}")
    public ResponseEntity<MovieCompleteDto> findMovieComplete(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @Operation(summary = "Traer una Película o varias películas filtradas u ordenadas")
    @GetMapping
    public ResponseEntity<?> findMovies(@RequestParam(required = false) String name, @RequestParam(required = false) Long genre, @RequestParam(required = false) String order)throws ResourceNotFoundException  {
        if(name != null){
            return ResponseEntity.ok().body(movieService.getByTitle(name));
        } else if(genre != null) {
            return ResponseEntity.ok().body(movieService.getByGenre(genre));
        } else if(order != null){
            return ResponseEntity.ok().body(movieService.getOrder(order));
        } else {
            return ResponseEntity.ok().body(movieService.getAll());
        }
    }

    //* ///////// DELETE ///////// *//

    @Operation(summary = "Eliminar Persona por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
