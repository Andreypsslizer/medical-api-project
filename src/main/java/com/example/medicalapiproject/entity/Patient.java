package com.example.medicalapiproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @NotNull
    private Date birthDate;

    @NotBlank
    private String gender;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String medicalCardNumber;

    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @JsonIgnore
    @OneToMany(
            mappedBy = "patient"
    )
    private List<Appointment> appointments;
}
