package com.dh.movieservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties()
public class Genre implements Serializable {

    @Serial
	private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seriePeliculaSequence",sequenceName = "seriePeliculaSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seriePeliculaSequence")
    private Long id;

    @Column
    private String name;

    @Column
    private String image;

    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Movie> movies;

}
