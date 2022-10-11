package com.dh.userservice.api.service;

import com.dh.userservice.Exceptions.BadRequestException;
import com.dh.userservice.Exceptions.ResourceNotFoundException;
import com.dh.userservice.api.service.Security.AuthenticationService;
import com.dh.userservice.api.service.Security.MyPasswordEncoder;
import com.dh.userservice.api.service.Security.jwt.JwtUtil;
import com.dh.userservice.domain.dto.UserShowDto;
import com.dh.userservice.domain.mapper.UserMapper;
import com.dh.userservice.domain.model.AuthenticationRequest;
import com.dh.userservice.domain.model.Rol;
import com.dh.userservice.domain.model.User;
import com.dh.userservice.domain.model.UserRoles;
import com.dh.userservice.domain.repository.IUserRepository;
import com.dh.userservice.shared.GenericServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends GenericServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;

    private final MyPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationService authenticationService;

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        String hashedPassword = passwordEncoder.encodePassword(user.getPassword());
        user.setPassword(hashedPassword);
        if (user.getId() == null){
            Rol rol = new Rol();
            rol.setId(1L);
            rol.setName(UserRoles.USER);
            user.setRol(rol);
            User userSave = userRepository.save(user);
            log.info("Usuario registrado correctamente: "+ userSave);
            return userSave;
        } else {
            User userSave = userRepository.save(user);
            log.info("Usuario actualizado correctamente: "+ userSave);
            return userSave;
        }
    }

    @Override
    public UserShowDto authenticate(AuthenticationRequest authenticationRequest) throws BadRequestException {
        Optional<User> user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user.isPresent() && passwordEncoder.matchesPassword(authenticationRequest.getPassword(), user.get().getPassword())) {
            final UserDetails userDetails = authenticationService.loadUserByUsername(authenticationRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            UserShowDto userCardDto = userMapper.toUserShowDto(user.get(), user.get().getRol().getName().name(), jwt);
            return userCardDto;
        } else {
            throw new BadRequestException("Los datos ingresados no son correctos");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        User user = userMapper.validador(id);
        userRepository.deleteById(id);
        log.info("Se eliminó el Usuario correctamente: id("+id+")");
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }
}
