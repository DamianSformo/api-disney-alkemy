package com.dh.userservice.domain.mapper;

import com.dh.userservice.Exceptions.ResourceNotFoundException;
import com.dh.userservice.domain.dto.UserShowDto;
import com.dh.userservice.domain.model.User;
import com.dh.userservice.domain.repository.IUserRepository;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements IValidador<User>{

    @Autowired
    IUserRepository repository;

    @BeforeMapping
    public User validador(Long id) throws ResourceNotFoundException {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(msjError + id);
        }
        return user.get();
    }

    public UserShowDto toUserShowDto(User user, String rolName, String jwt){
        return UserShowDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .rolName(rolName)
                .jwt(jwt)
                .build();
    }

}