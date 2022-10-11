package com.dh.movieservice.domain.model.send;

import com.dh.movieservice.domain.model.Movie;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendMovie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Movie movie;
    private String action;

    public SendMovie() {}
}
