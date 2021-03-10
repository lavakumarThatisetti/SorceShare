package com.sorceshare.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name" , length = 20)
    private ERole name;
}
