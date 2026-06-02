package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.DoctorDTO;
import com.example.medicalapiproject.entity.Doctor;
import com.example.medicalapiproject.entity.Role;
import com.example.medicalapiproject.entity.User;
import com.example.medicalapiproject.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Doctor create(DoctorDTO doctorDTO) {
        User user = userService.createUser(doctorDTO.getUser(), Role.ROLE_DOCTOR);
        Doctor doctor = new Doctor();

        doctor.setUser(user);
        doctor.setFullName(doctorDTO.getFullName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setCabinet(doctorDTO.getCabinet());
        doctor.setPhone(doctorDTO.getPhone());

        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor update(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));

        doctor.setFullName(doctorDTO.getFullName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setCabinet(doctorDTO.getCabinet());
        doctor.setPhone(doctorDTO.getPhone());

        return doctorRepository.save(doctor);
    }

    @Transactional
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor not found with id: " + id);
        }

        doctorRepository.deleteById(id);
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> searchByFullName(String fullName) {
        return doctorRepository.findByFullName(fullName);
    }

    public List<Doctor> filterBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }
}
