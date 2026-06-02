package com.example.medicalapiproject.controller;

import com.example.medicalapiproject.dto.DoctorDTO;
import com.example.medicalapiproject.dto.PatientDTO;
import com.example.medicalapiproject.entity.Doctor;
import com.example.medicalapiproject.entity.Patient;
import com.example.medicalapiproject.repository.UserRepository;
import com.example.medicalapiproject.service.DoctorService;
import com.example.medicalapiproject.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register/patient")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody PatientDTO patientDTO) {
        Patient patient = patientService.create(patientDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PostMapping("/register/doctor")
    //@PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<Doctor> registerDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = doctorService.create(doctorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        ));
    }
}
