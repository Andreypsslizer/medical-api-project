package com.example.medicalapiproject.repository;

import com.example.medicalapiproject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFullName(String fullName);

    List<Doctor> findBySpecialization(String specialization);
}
