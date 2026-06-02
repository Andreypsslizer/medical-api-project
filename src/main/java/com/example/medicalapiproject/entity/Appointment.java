package com.example.medicalapiproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private MedicalService service;

    @NotBlank
    private Timestamp timestamp;


    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(255)")
    private Set<Status> status;


    private String notes;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord record;
}
