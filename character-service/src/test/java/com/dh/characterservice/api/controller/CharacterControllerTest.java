package com.dh.characterservice.api.controller;

import com.dh.characterservice.api.service.CharacterServiceImpl;
import com.dh.characterservice.domain.mapper.CharacterMapper;
import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.dto.CharacterCompleteDto;
import com.dh.characterservice.domain.model.dto.CharacterShowDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CharacterController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class CharacterControllerTest {

    @MockBean
    private CharacterServiceImpl characterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void save() throws Exception {

        Character character1 = new Character();
        character1.setId(1L);
        character1.setName("test.1");
        character1.setImage("url.test.1");

        when(characterService.save(character1)).thenReturn(character1);

        mvc.perform( MockMvcRequestBuilders
                .post("/")
                .content(objectMapper.writeValueAsString(character1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Character character2 = new Character();
        character2.setName("test.2");

        when(characterService.save(character2)).thenReturn(character2);

        mvc.perform( MockMvcRequestBuilders
                .post("/")
                .content(objectMapper.writeValueAsString(character2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void findCharacterComplete() throws Exception {

        when(characterService.getById(1L)).thenReturn(CharacterCompleteDto.builder()
                .id(1L)
                .image("url.test")
                .name("test")
                .age(42)
                .weight(66.3)
                .build());

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("url.test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(66.3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.history").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").isNumber());

        verify(characterService).getById(1L);

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());

        verify(characterService).getById(2L);

        mvc.perform(MockMvcRequestBuilders
                .get("/detail/{id}", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void findCharacters() throws Exception {

        List<CharacterShowDto> characterShowDtos = new ArrayList<>();
        List<CharacterShowDto> characterShowDtosFilterAgeAndWeight = new ArrayList<>();

        Character character1 = new Character();
        character1.setId(1L);
        character1.setImage("url.test.1");
        character1.setName("test.1");
        character1.setAge(42);
        character1.setWeight(66.3);

        CharacterShowDto characterShowDto1 = CharacterShowDto.builder()
                .id(1L)
                .image("url.test.1")
                .name("test.1")
                .build();

        Character character2 = new Character();
        character2.setId(2L);
        character2.setName("test.2");
        character2.setAge(21);
        character2.setWeight(154.8);

        CharacterShowDto characterShowDto2 = CharacterShowDto.builder()
                .id(2L)
                .name("test.2")
                .build();

        Character character3 = new Character();
        character3.setId(3L);
        character3.setImage("url.test.3");
        character3.setName("test.3");
        character3.setAge(21);
        character3.setWeight(78.6);

        CharacterShowDto characterShowDto3 = CharacterShowDto.builder()
                .id(1L)
                .image("url.test.3")
                .name("test.3")
                .build();

        characterShowDtos.add(characterShowDto1);
        characterShowDtos.add(characterShowDto2);
        characterShowDtos.add(characterShowDto3);

        characterShowDtosFilterAgeAndWeight.add(characterShowDto2);
        characterShowDtosFilterAgeAndWeight.add(characterShowDto3);

        when(characterService.getAll()).thenReturn(characterShowDtos);

        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value("url.test.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].image").isEmpty());

        verify(characterService).getAll();

        List<Double> weight = new ArrayList<>();
        weight.add(35.5);
        weight.add(195.5);

        when(characterService.filter(null, 21, weight)).thenReturn(characterShowDtosFilterAgeAndWeight);

        mvc.perform(MockMvcRequestBuilders
                .get("/?age=21&weight=35.5,195.5")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("test.3"));

        verify(characterService).filter(null, 21, weight);

        when(characterService.filter(null, 21, null)).thenReturn(characterShowDtosFilterAgeAndWeight);

        mvc.perform(MockMvcRequestBuilders
                .get("/21")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

        when(characterService.getByName("test.1")).thenReturn(characterShowDto1);

        mvc.perform(MockMvcRequestBuilders
                .get("/?name=test.1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test.1"));

        verify(characterService).getByName("test.1");

    }

    @Test
    void delete() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/{id}", 1) )
                .andExpect(status().isNoContent());

        mvc.perform(MockMvcRequestBuilders.delete("/{id}", "test") )
                .andExpect(status().isBadRequest());
    }
}