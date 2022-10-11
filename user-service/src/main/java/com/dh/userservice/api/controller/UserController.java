package com.dh.userservice.api.controller;

import com.dh.userservice.Exceptions.BadRequestException;
import com.dh.userservice.Exceptions.ResourceNotFoundException;
import com.dh.userservice.api.service.IUserService;
import com.dh.userservice.domain.dto.UserShowDto;
import com.dh.userservice.domain.model.AuthenticationRequest;
import com.dh.userservice.domain.model.User;
import com.dh.userservice.api.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RefreshScope
@RestController
public class UserController {

    private IUserService userService;

    private MailService mailService;

    public UserController(IUserService userService, MailService mailService)
    {
        this.userService = userService;
        this.mailService = mailService;
    }

    //* ///////// POST ///////// *//

    @Operation(summary = "Guardar o actualizar un Usuario")
    @PostMapping("/register")
    public ResponseEntity<UserShowDto> save(@RequestBody User user) throws BadRequestException, MessagingException, IOException {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(user.getEmail());
        authenticationRequest.setPassword(user.getPassword());

        if(user.getId() == null){
            userService.save(user);
            UserShowDto userShowDto = userService.authenticate(authenticationRequest);
            mailService.send(user.getEmail(), user.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(userShowDto);
        } else{
            userService.save(user);
            return ResponseEntity.ok(userService.authenticate(authenticationRequest));
        }
    }

    @Operation(summary = "Log in de Usuario")
    @PostMapping("/authenticate")
    public ResponseEntity<UserShowDto> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadRequestException {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }

    //* ///////// DELETE ///////// *//

    @Operation(summary = "Eliminar un Usuario por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
