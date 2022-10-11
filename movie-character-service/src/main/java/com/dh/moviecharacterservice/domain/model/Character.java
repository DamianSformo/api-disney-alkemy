package com.dh.moviecharacterservice.domain.model;

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
@JsonIgnoreProperties({"movies"})
public class Character implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "characterSequence",sequenceName = "characterSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "characterSequence")
    private Long id;

    @Column
    private String image;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private Double weight;

    @Lob
    private String history;

    @OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<MovieCharacter> movies;

}

