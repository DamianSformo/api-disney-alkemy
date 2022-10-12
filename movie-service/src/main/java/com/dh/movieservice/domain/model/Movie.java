package com.dh.movieservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({})
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "movieSequence",sequenceName = "movieSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieSequence")
    private Long id;

    @Column
    private String image;

    @Column
    private String title;

    @Column
    private Integer rating;

    @Column
    private Date dateCreated;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Genre genre;

}

