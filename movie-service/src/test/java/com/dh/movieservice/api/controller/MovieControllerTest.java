package com.dh.movieservice.api.controller;

import com.dh.movieservice.api.service.MovieService;
import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.model.dto.MovieCompleteDto;
import com.dh.movieservice.domain.model.dto.MovieShowDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mvc;

    @Test
    void save() throws Exception {

        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("test.1");
        movie1.setImage("url.test.1");

        when(movieService.save(movie1)).thenReturn(movie1);

        mvc.perform( MockMvcRequestBuilders
                .post("/")
                .content(objectMapper.writeValueAsString(movie1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Movie movie2 = new Movie();
        movie2.setTitle("test.2");

        when(movieService.save(movie2)).thenReturn(movie2);

        mvc.perform( MockMvcRequestBuilders
                .post("/")
                .content(objectMapper.writeValueAsString(movie2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void findMovieComplete() throws Exception {

        when(movieService.getById(1L)).thenReturn(MovieCompleteDto.builder()
                .id(1L)
                .image("url.test")
                .title("test")
                .rating(3)
                .dateCreated("2022-12-12")
                .build());

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("url.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").isNumber());

        verify(movieService).getById(1L);

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());

        verify(movieService).getById(2L);

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
}

    @Test
    void findMovies() throws Exception {

        List<MovieShowDto> movieShowDtos = new ArrayList<>();

        MovieShowDto movieShowDto1 = MovieShowDto.builder()
                .id(1L)
                .image("url.test.1")
                .title("test.1")
                .build();
        movieShowDtos.add(movieShowDto1);

        MovieShowDto movieShowDto2 = MovieShowDto.builder()
                .id(2L)
                .image("url.test.2")
                .title("test.2")
                .dateCreated("2021-12-12")
                .build();
        movieShowDtos.add(movieShowDto2);

        when(movieService.getAll()).thenReturn(movieShowDtos);

        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("url.test.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateCreated").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("test.2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateCreated").value("2021-12-12"));

        verify(movieService).getAll();

        when(movieService.getByTitle("test.1")).thenReturn(movieShowDto1);

        mvc.perform(MockMvcRequestBuilders
                .get("/?name=test.1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test.1"));

        verify(movieService).getByTitle("test.1");

        when(movieService.getOrder("DESC")).thenReturn(movieShowDtos);

        mvc.perform(MockMvcRequestBuilders
                .get("/?order=DESC")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("test.1"));

        verify(movieService).getOrder("DESC");
    }

    @Test
    void delete() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/{id}", 1) )
                .andExpect(status().isNoContent());

        mvc.perform(MockMvcRequestBuilders.delete("/{id}", "test") )
                .andExpect(status().isBadRequest());
    }
}