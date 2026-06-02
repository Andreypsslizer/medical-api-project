package com.example.medicalapiproject.repository;

import com.example.medicalapiproject.entity.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
    List<MedicalService> findByName(String name);
}
