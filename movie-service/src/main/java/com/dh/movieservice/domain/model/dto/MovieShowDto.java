package com.dh.movieservice.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Objects;

@ToString
@Getter
@Setter
@Builder
public class MovieShowDto {

    private Long id;

    private String image;

    private String title;

    private String dateCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieShowDto that = (MovieShowDto) o;
        return Objects.equals(id, that.id) && Objects.equals(image, that.image) && Objects.equals(title, that.title) && Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, title, dateCreated);
    }
}
