package com.dh.characterservice.domain.model.send;

import com.dh.characterservice.domain.model.Character;
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