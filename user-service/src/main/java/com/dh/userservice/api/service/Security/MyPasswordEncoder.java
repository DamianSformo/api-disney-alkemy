package com.dh.userservice.api.service.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyPasswordEncoder{
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public String encodePassword (String rawPassword) {
        return bCryptPasswordEncoder().encode(rawPassword);
    }
    public boolean matchesPassword(String rawPassword,String encodePassword){
        return bCryptPasswordEncoder().matches(rawPassword,encodePassword);
    }
}
