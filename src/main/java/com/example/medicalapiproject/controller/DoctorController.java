package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.DoctorDTO;
import com.example.medicalapiproject.entity.Doctor;
import com.example.medicalapiproject.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Doctor> create(@Valid @RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = doctorService.create(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorDTO doctorDTO
    ) {
        Doctor doctor = doctorService.update(id, doctorDTO);

        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);

        return ResponseEntity.ok(doctor);
    }
}
