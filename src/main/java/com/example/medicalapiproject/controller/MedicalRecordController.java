package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.MedicalRecordDTO;
import com.example.medicalapiproject.entity.MedicalRecord;
import com.example.medicalapiproject.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<MedicalRecord> create(
            @Valid @RequestBody MedicalRecordDTO medicalRecordDTO
    ) {
        MedicalRecord medicalRecord = medicalRecordService.create(medicalRecordDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<MedicalRecord> findById(@PathVariable Long id) {
        MedicalRecord medicalRecord = medicalRecordService.findById(id);

        return ResponseEntity.ok(medicalRecord);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(medicalRecordService.findAll());
    }

    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<MedicalRecord> findByAppointmentId(
            @PathVariable Long appointmentId
    ) {
        MedicalRecord medicalRecord = medicalRecordService.findByAppointmentId(appointmentId);

        return ResponseEntity.ok(medicalRecord);
    }

    @GetMapping("/patient/{patientId}/history")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<?> getPatientHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getPatientHistory(patientId));
    }
}
