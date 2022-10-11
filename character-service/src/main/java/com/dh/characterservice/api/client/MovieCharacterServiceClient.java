package com.dh.characterservice.api.client;

import com.dh.characterservice.domain.model.dto.CharacterShowDto;
import com.dh.characterservice.domain.model.dto.MovieShowDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="http://localhost:8021/movies-characters/")
public interface MovieCharacterServiceClient {

    @GetMapping("/movies/{characterId}")
    ResponseEntity<List<MovieShowDto>> getMoviesByCharacterId(@PathVariable Long characterId);

    @GetMapping("/characters/{movieId}")
    ResponseEntity<List<CharacterShowDto>> getCharactersByMovieId(@PathVariable Long movieId);

}

