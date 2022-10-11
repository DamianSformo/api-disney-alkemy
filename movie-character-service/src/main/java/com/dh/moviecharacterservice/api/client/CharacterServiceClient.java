package com.dh.moviecharacterservice.api.client;

import com.dh.moviecharacterservice.domain.model.Character;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="http://localhost:8011/characters/")
public interface CharacterServiceClient {

    @PostMapping("/")
    ResponseEntity<Character> save(@RequestBody Character character);
}
