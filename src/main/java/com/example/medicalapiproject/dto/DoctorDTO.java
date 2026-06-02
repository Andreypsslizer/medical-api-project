package com.example.medicalapiproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDTO {

    @Valid
    @NotNull(message = "Enter the user datda please!")
    private UserCreateDTO user;

    @NotBlank(message = "The full Name cannot be blank")
    private String fullName;

    @NotBlank(message = "The specialization cannot be blank")
    private String specialization;

    @NotNull(message = "The experience cannot be null")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    @NotBlank(message = "The cabinet cannot be blank")
    private String cabinet;

    @NotBlank(message = "The phone cannot be blank")
    private String phone;
}
