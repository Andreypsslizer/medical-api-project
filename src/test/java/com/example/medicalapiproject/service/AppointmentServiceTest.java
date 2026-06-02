package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.AppointmentDTO;
import com.example.medicalapiproject.entity.Doctor;
import com.example.medicalapiproject.entity.MedicalService;
import com.example.medicalapiproject.entity.Patient;
import com.example.medicalapiproject.entity.Status;
import com.example.medicalapiproject.repository.AppointmentRepository;
import com.example.medicalapiproject.repository.DoctorRepository;
import com.example.medicalapiproject.repository.MedicalServiceRepository;
import com.example.medicalapiproject.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private MedicalServiceRepository medicalServiceRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void create_ShouldThrowException_WhenDoctorIsBusy() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2026, 1, 10, 10, 0));

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setPatientId(1L);
        appointmentDTO.setDoctorId(2L);
        appointmentDTO.setMedicalServiceId(3L);
        appointmentDTO.setTimestamp(timestamp);

        Patient patient = new Patient();
        patient.setId(1L);

        Doctor doctor = new Doctor();
        doctor.setId(2L);

        MedicalService medicalService = new MedicalService();
        medicalService.setId(3L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(medicalServiceRepository.findById(3L)).thenReturn(Optional.of(medicalService));

        when(appointmentRepository.existsByDoctorAndTimestampAndStatusIn(
                doctor,
                timestamp,
                Set.of(Status.CREATED, Status.CONFIRMED)
        )).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> appointmentService.create(appointmentDTO));
    }
}