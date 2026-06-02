package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.PatientDTO;
import com.example.medicalapiproject.entity.Patient;
import com.example.medicalapiproject.entity.Role;
import com.example.medicalapiproject.entity.User;
import com.example.medicalapiproject.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Patient create(PatientDTO patientDTO) {
        User user = userService.createUser(patientDTO.getUser(), Role.ROLE_PATIENT);
        Patient patient = new Patient();

        patient.setUser(user);
        patient.setFullName(patientDTO.getFullName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setAddress(patientDTO.getAddress());
        patient.setMedicalCardNumber(patientDTO.getMedicalCardNumber());


        return patientRepository.save(patient);
    }

    @Transactional
    public Patient update(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));

        patient.setFullName(patientDTO.getFullName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setAddress(patientDTO.getAddress());
        patient.setMedicalCardNumber(patientDTO.getMedicalCardNumber());

        return patientRepository.save(patient);
    }

    @Transactional
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }

        patientRepository.deleteById(id);
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> searchByFullName(String fullName) {
        return patientRepository.findByFullName(fullName);
    }

    public Optional<Patient> searchByMedicalCardNumber(String medicalCardNumber) {
        return patientRepository.findByMedicalCardNumber(medicalCardNumber);
    }

}
