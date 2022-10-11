package com.dh.moviecharacterservice.api.client;

import com.dh.moviecharacterservice.domain.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="http://localhost:8001/movies/")
public interface MovieServiceClient {

    @PostMapping("/")
    ResponseEntity<Movie> save(@RequestBody Movie movie);
}

