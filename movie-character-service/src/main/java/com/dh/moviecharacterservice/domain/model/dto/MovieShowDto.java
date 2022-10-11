package com.dh.moviecharacterservice.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@Builder
public class MovieShowDto {

    private Long id;

    private String image;

    private String title;

    private String dateCreated;

}
