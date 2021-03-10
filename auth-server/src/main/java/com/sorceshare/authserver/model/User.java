package com.sorceshare.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Access(AccessType.FIELD)
@JsonIgnoreProperties
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_name"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends Audit {

    private static final long serialVersionUID = -1312828236169257851L;

    @Column(name="first_name")
    @NotBlank
    @Size(min= 4, max = 20)
    private String firstName;

    @Column(name="last_name")
    @Size(max = 20)
    private String lastName;

    @Column(name="user_name")
    @NotNull
    @Size(max = 30)
    private String userName;

    @Column(name="email")
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    @Column(name="phone_no")
    @NotBlank
    @Size(max = 15)
    private String phoneNo;

    @Column(name="dob")
    private String dob;

    @Column(name="gender")
    @NotBlank
    private String gender;

    @Column(name="profession")
    @NotBlank
    private String profession;

    @Column(name="password")
    @NotBlank
    @Size(max = 150)
    private String password;

    @Column(name="active")
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


}
