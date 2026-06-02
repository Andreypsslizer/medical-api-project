package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.PatientDTO;
import com.example.medicalapiproject.entity.Patient;
import com.example.medicalapiproject.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody PatientDTO patientDTO) {
        Patient patient = patientService.create(patientDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(
            @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO
    ) {
        Patient patient = patientService.update(id, patientDTO);

        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);

        return ResponseEntity.ok(patient);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String medicalCardNumber
    ) {
        if (fullName != null && !fullName.isBlank()) {
            return ResponseEntity.ok(patientService.searchByFullName(fullName));
        }

        if (medicalCardNumber != null && !medicalCardNumber.isBlank()) {
            return ResponseEntity.ok(patientService.searchByMedicalCardNumber(medicalCardNumber));
        }

        return ResponseEntity.ok(patientService.findAll());
    }
}
