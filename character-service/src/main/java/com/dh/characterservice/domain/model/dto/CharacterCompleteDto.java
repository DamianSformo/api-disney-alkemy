package com.dh.characterservice.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Lob;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class CharacterCompleteDto {

    private Long id;

    private String image;

    private String name;

    private Integer age;

    private Double weight;

    @Lob
    private String history;

    private List<MovieShowDto> movies;

}
