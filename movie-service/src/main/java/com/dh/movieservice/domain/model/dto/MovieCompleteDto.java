package com.dh.movieservice.domain.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class MovieCompleteDto {

    private Long id;

    private String image;

    private String title;

    private Integer rating;

    private String dateCreated;

    private String genre;

    private List<CharacterShowDto> characters;
}
