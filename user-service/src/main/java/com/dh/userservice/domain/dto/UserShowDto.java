package com.dh.userservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class UserShowDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String rolName;
    private String jwt;

}
