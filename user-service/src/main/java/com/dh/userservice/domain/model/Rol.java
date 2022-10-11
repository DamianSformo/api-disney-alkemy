package com.dh.userservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
public class Rol {

    @Id
    @SequenceGenerator(name = "rolSequence",sequenceName = "rolSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rolSequence")
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRoles name;

    @OneToMany(mappedBy = "rol")
    @ToString.Exclude
    private Set<User> users;

}
