package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.MedicalServiceDTO;
import com.example.medicalapiproject.entity.MedicalService;
import com.example.medicalapiproject.service.MedicalServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-services")
@RequiredArgsConstructor
public class MedicalServiceController {

    @Autowired
    private MedicalServiceService medicalServiceService;

    @PostMapping
    public ResponseEntity<MedicalService> create(
            @Valid @RequestBody MedicalServiceDTO medicalServiceDTO
    ) {
        MedicalService medicalService = medicalServiceService.create(medicalServiceDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicalService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalService> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalServiceDTO medicalServiceDTO
    ) {
        MedicalService medicalService = medicalServiceService.update(id, medicalServiceDTO);

        return ResponseEntity.ok(medicalService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalServiceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<MedicalService> findById(@PathVariable Long id) {
        MedicalService medicalService = medicalServiceService.findById(id);

        return ResponseEntity.ok(medicalService);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name
    ) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(medicalServiceService.searchByName(name));
        }

        return ResponseEntity.ok(medicalServiceService.findAll());
    }
}
