package com.example.medicalapiproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank(message = "The password cannot be blank")
    @Size(min = 8, message = "The password must contain at least 8 characters")
    @JsonIgnore
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(255)")
    private Set<Role> roles;

    @OneToOne(mappedBy = "user")
    private Patient patient;

    @OneToOne(mappedBy = "user")
    private Doctor doctor;
}
