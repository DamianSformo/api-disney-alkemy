package com.dh.characterservice.api.controller;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;
import com.dh.characterservice.api.service.CharacterServiceImpl;
import com.dh.characterservice.domain.model.Character;
import com.dh.characterservice.domain.model.dto.CharacterCompleteDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class CharacterController {

    private final CharacterServiceImpl characterService;

    public CharacterController(CharacterServiceImpl characterService) {
        this.characterService = characterService;
    }

    //* ///////// POST ///////// *//

    @Operation(summary = "Guardar o actualizar un Personaje")
    @PostMapping("")
    public ResponseEntity<Character> save(@RequestBody Character character) {
        if(character.getId() == null)
            return ResponseEntity.status(HttpStatus.CREATED).body(characterService.save(character));
        else
            return ResponseEntity.ok(characterService.save(character));
    }

    //* ///////// GET ///////// *//

    @Operation(summary = "Traer un Personaje Completo por Id")
    @GetMapping("/detail/{id}")
    public ResponseEntity<CharacterCompleteDto> findCharacterComplete(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(characterService.getById(id));
    }

    @Operation(summary = "Traer un Personaje o varios Personajes filtrados")
    @GetMapping
    public ResponseEntity<?> findCharacters(@RequestParam(required = false) String name, @RequestParam(required = false) Long movieId, @RequestParam(required = false) Integer age, @RequestParam(required = false) List<Double> weight) throws ResourceNotFoundException {
        if(name != null) {
            return ResponseEntity.ok().body(characterService.getByName(name));
        } else if((movieId != null) || (age != null) || (weight != null)){
            return ResponseEntity.ok().body(characterService.filter(movieId, age, weight));
        } else {
            return ResponseEntity.ok().body(characterService.getAll());
        }
    }

    //* ///////// DELETE ///////// *//

    @Operation(summary = "Eliminar Personaje por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException {
        characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

