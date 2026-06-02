package com.example.medicalapiproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AppointmentDTO {
    @NotNull(message = "Patient id cannot be null")
    private Long patientId;

    @NotNull(message = "Doctor id cannot be null")
    private Long doctorId;

    @NotNull(message = "Medical service id cannot be null")
    private Long medicalServiceId;

    @NotNull(message = "Appointment timestamp cannot be null")
    private Timestamp timestamp;

    private String notes;
}
