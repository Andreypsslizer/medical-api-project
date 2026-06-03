package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.AppointmentDTO;
import com.example.medicalapiproject.entity.*;
import com.example.medicalapiproject.repository.AppointmentRepository;
import com.example.medicalapiproject.repository.DoctorRepository;
import com.example.medicalapiproject.repository.MedicalServiceRepository;
import com.example.medicalapiproject.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Transactional
    public Appointment create(AppointmentDTO appointmentDTO) {
        Patient patient = getPatientById(appointmentDTO.getPatientId());
        Doctor doctor = getDoctorById(appointmentDTO.getDoctorId());
        MedicalService medicalService = getMedicalServiceById(appointmentDTO.getMedicalServiceId());

        checkDoctorIsFree(doctor, appointmentDTO);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setService(medicalService);
        appointment.setTimestamp(appointmentDTO.getTimestamp());
        appointment.setNotes(appointmentDTO.getNotes());
        appointment.setStatus(new HashSet<>(Set.of(Status.CREATED)));

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment update(Long id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Appointment not found with id: " + id
                ));

        Patient patient = getPatientById(appointmentDTO.getPatientId());
        Doctor doctor = getDoctorById(appointmentDTO.getDoctorId());
        MedicalService medicalService = getMedicalServiceById(appointmentDTO.getMedicalServiceId());

        checkDoctorIsFreeForUpdate(doctor, appointmentDTO, id);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setService(medicalService);
        appointment.setTimestamp(appointmentDTO.getTimestamp());
        appointment.setNotes(appointmentDTO.getNotes());

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Appointment not found with id: " + id
                ));

        appointment.setStatus(new HashSet<>(Set.of(Status.CREATED)));

        return appointmentRepository.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Appointment not found with id: " + id
                ));
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientIdOrderByTimestampDesc(patientId);
    }

    private void checkDoctorIsFree(Doctor doctor, AppointmentDTO appointmentDTO) {
        boolean doctorIsBusy = appointmentRepository.existsByDoctorAndTimestampAndStatusIn(
                doctor,
                appointmentDTO.getTimestamp(),
                Set.of(Status.CREATED, Status.CONFIRMED)
        );

        if (doctorIsBusy) {
            throw new IllegalStateException("Doctor is busy at this time");
        }
    }

    private void checkDoctorIsFreeForUpdate(
            Doctor doctor,
            AppointmentDTO appointmentDTO,
            Long appointmentId
    ) {
        boolean doctorIsBusy = appointmentRepository.existsByDoctorAndTimestampAndStatusInAndIdNot(
                doctor,
                appointmentDTO.getTimestamp(),
                Set.of(Status.CREATED, Status.CONFIRMED),
                appointmentId
        );

        if (doctorIsBusy) {
            throw new IllegalStateException("Doctor is busy at this time");
        }
    }

    private Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Patient not found with id: " + patientId
                ));
    }

    private Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor not found with id: " + doctorId
                ));
    }

    private MedicalService getMedicalServiceById(Long medicalServiceId) {
        return medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Medical service not found with id: " + medicalServiceId
                ));
    }
}
