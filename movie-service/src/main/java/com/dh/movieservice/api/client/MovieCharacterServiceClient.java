package com.dh.movieservice.api.client;

import com.dh.movieservice.domain.model.dto.CharacterShowDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="http://localhost:8021/movies-characters/")
public interface MovieCharacterServiceClient {

    @GetMapping("/characters/{movieId}")
    ResponseEntity<List<CharacterShowDto>> getCharactersByMovieId(@PathVariable Long movieId);

}

