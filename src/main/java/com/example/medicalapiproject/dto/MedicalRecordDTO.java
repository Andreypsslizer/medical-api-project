package com.example.medicalapiproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordDTO {
    @NotNull(message = "Appointment id cannot be null")
    private Long appointmentId;

    @NotBlank(message = "Diagnosis cannot be blank")
    private String diagnosis;

    @NotBlank(message = "Treatment cannot be blank")
    private String treatment;

    private String doctorNotes;
}
