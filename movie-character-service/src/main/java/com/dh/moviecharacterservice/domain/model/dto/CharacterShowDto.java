package com.dh.moviecharacterservice.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class CharacterShowDto {

    private Long id;

    private String image;

    private String name;
}
