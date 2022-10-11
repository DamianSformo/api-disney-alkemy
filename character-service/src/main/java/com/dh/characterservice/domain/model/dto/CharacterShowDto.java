package com.dh.characterservice.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter
@Setter
@Builder
public class CharacterShowDto {

    private Long id;

    private String image;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterShowDto that = (CharacterShowDto) o;
        return Objects.equals(id, that.id) && Objects.equals(image, that.image) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name);
    }
}
