package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.MedicalRecordDTO;
import com.example.medicalapiproject.entity.Appointment;
import com.example.medicalapiproject.entity.Status;
import com.example.medicalapiproject.repository.AppointmentRepository;
import com.example.medicalapiproject.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    void create_ShouldThrowException_WhenAppointmentIsCancelled() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setAppointmentId(1L);
        medicalRecordDTO.setDiagnosis("Flu");
        medicalRecordDTO.setTreatment("Rest and medication");
        medicalRecordDTO.setDoctorNotes("Patient needs follow-up");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(Set.of(Status.CANCELLED));

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(medicalRecordRepository.existsByAppointmentId(1L)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> medicalRecordService.create(medicalRecordDTO));
    }
}