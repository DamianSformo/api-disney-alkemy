package com.dh.userservice.domain.data;

import com.dh.userservice.domain.model.Rol;
import com.dh.userservice.domain.model.UserRoles;
import com.dh.userservice.domain.repository.IRolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final IRolRepository rolRepository;

    public DataLoader(IRolRepository rolRepository) {
        this.rolRepository = rolRepository;}

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var user = new Rol();
        user.setId(1L);
        user.setName(UserRoles.USER);
        rolRepository.save(user);

        var admin = new Rol();
        user.setId(2L);
        user.setName(UserRoles.ADMIN);
        rolRepository.save(admin);
    }
}
