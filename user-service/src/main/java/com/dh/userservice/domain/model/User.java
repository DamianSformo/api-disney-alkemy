package com.dh.userservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"characters"})
public class User{

    @Id
    @SequenceGenerator(name = "userSequence",sequenceName = "userSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol" )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rol rol;
}
