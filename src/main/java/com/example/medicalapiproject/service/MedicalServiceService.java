package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.MedicalServiceDTO;
import com.example.medicalapiproject.entity.MedicalService;
import com.example.medicalapiproject.repository.MedicalServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalServiceService {

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Transactional
    public MedicalService create(MedicalServiceDTO medicalServiceDTO) {
        MedicalService medicalService = new MedicalService();
        fillMedicalServiceFromDto(medicalService, medicalServiceDTO);

        return medicalServiceRepository.save(medicalService);
    }

    @Transactional
    public MedicalService update(Long id, MedicalServiceDTO medicalServiceDTO) {
        MedicalService medicalService = medicalServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Medical service not found with id: " + id
                ));

        fillMedicalServiceFromDto(medicalService, medicalServiceDTO);

        return medicalServiceRepository.save(medicalService);
    }

    @Transactional
    public void delete(Long id) {
        if (!medicalServiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Medical service not found with id: " + id);
        }

        medicalServiceRepository.deleteById(id);
    }

    public MedicalService findById(Long id) {
        return medicalServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Medical service not found with id: " + id
                ));
    }

    public List<MedicalService> findAll() {
        return medicalServiceRepository.findAll();
    }

    public List<MedicalService> searchByName(String name) {
        return medicalServiceRepository.findByName(name);
    }

    private void fillMedicalServiceFromDto(
            MedicalService medicalService,
            MedicalServiceDTO medicalServiceDTO
    ) {
        medicalService.setName(medicalServiceDTO.getName());
        medicalService.setDescription(medicalServiceDTO.getDescription());
        medicalService.setPrice(medicalServiceDTO.getPrice());
        medicalService.setDuration_minutes(medicalServiceDTO.getDurationMinutes());
    }
}
