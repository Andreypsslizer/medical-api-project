package com.example.medicalapiproject.repository;

import com.example.medicalapiproject.entity.Appointment;
import com.example.medicalapiproject.entity.Doctor;
import com.example.medicalapiproject.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorAndTimestampAndStatusIn(
            Doctor doctor,
            Timestamp timestamp,
            Collection<Status> statuses
    );

    boolean existsByDoctorAndTimestampAndStatusInAndIdNot(
            Doctor doctor,
            Timestamp timestamp,
            Collection<Status> statuses,
            Long id
    );

    List<Appointment> findByPatientIdOrderByTimestampDesc(Long patientId);
}
