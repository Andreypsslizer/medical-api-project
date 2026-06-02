package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.MedicalRecordDTO;
import com.example.medicalapiproject.entity.Appointment;
import com.example.medicalapiproject.entity.MedicalRecord;
import com.example.medicalapiproject.entity.Status;
import com.example.medicalapiproject.repository.AppointmentRepository;
import com.example.medicalapiproject.repository.MedicalRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public MedicalRecord create(MedicalRecordDTO medicalRecordDTO) {
        Appointment appointment = appointmentRepository.findById(medicalRecordDTO.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Appointment not found with id: " + medicalRecordDTO.getAppointmentId()
                ));

        if (medicalRecordRepository.existsByAppointmentId(appointment.getId())) {
            throw new IllegalStateException(
                    "Medical record already exists for appointment id: " + appointment.getId()
            );
        }

        if (appointment.getStatus().contains(Status.CANCELLED)) {
            throw new IllegalStateException("Cannot create medical record for cancelled appointment");
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAppointment(appointment);
        medicalRecord.setDiagnosis(medicalRecordDTO.getDiagnosis());
        medicalRecord.setTreatment(medicalRecordDTO.getTreatment());
        medicalRecord.setDoctorNotes(medicalRecordDTO.getDoctorNotes());

        appointment.setStatus(Set.of(Status.COMPLETED));
        appointmentRepository.save(appointment);

        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Medical record not found with id: " + id
                ));
    }

    public MedicalRecord findByAppointmentId(Long appointmentId) {
        return medicalRecordRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Medical record not found for appointment id: " + appointmentId
                ));
    }

    public List<MedicalRecord> getPatientHistory(Long patientId) {
        return medicalRecordRepository.findByAppointmentPatientIdOrderByAppointmentTimestampDesc(patientId);
    }

    public List<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }
}
