package com.example.medicalapiproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PatientDTO {

    @Valid
    @NotNull(message = "Enter the user datda please!")
    private UserCreateDTO user;

    @NotBlank(message = "The full Name cannot be blank")
    private String fullName;

    @NotNull(message = "The birth Date cannot be null")
    private Date birthDate;

    @NotBlank(message = "The gender cannot be blank")
    private String gender;

    @NotBlank(message = "The phone Number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "The address cannot be blank")
    private String address;

    @NotBlank(message = "The medical Card Number cannot be blank")
    private String medicalCardNumber;
}
