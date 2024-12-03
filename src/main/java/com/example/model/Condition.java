package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient_condition")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "condition_name")
    private String name;

    private String description;

    private LocalDate diagnosisDate;

    @Enumerated(EnumType.STRING)
    private ConditionStatus status;

    private Long patientId; // Storing the ID of the patient, not the full entity

    public Condition() {
    }

    public Condition(Long id, String name, String description, LocalDate diagnosisDate, ConditionStatus status, Long patientId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
        this.patientId = patientId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public ConditionStatus getStatus() {
        return status;
    }

    public void setStatus(ConditionStatus status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
