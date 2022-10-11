package com.dh.moviecharacterservice.domain.model.send;

import com.dh.moviecharacterservice.domain.model.Character;
import com.dh.moviecharacterservice.domain.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SendCharacter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Character character;
    private String action;

    public SendCharacter() {}
}