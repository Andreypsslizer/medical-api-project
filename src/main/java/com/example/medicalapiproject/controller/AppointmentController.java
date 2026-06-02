package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.AppointmentDTO;
import com.example.medicalapiproject.entity.Appointment;
import com.example.medicalapiproject.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<Appointment> create(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentService.create(appointmentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDTO appointmentDTO
    ) {
        Appointment appointment = appointmentService.update(id, appointmentDTO);

        return ResponseEntity.ok(appointment);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
        Appointment appointment = appointmentService.cancel(id);

        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);

        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Long patientId
    ) {
        if (patientId != null) {
            return ResponseEntity.ok(appointmentService.getPatientAppointments(patientId));
        }

        return ResponseEntity.ok(appointmentService.findAll());
    }
}
