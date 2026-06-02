package com.example.medicalapiproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization;

    @Min(0)
    private int experience;

    @NotBlank
    private String cabinet;

    @NotBlank
    private String phone;


    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
}
