package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthdate;

    private Long userId; // Referencing the user ID for search filtering

    @ElementCollection // Simple representation of conditions as strings
    @CollectionTable(name = "patient_conditions", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "condition")
    private List<String> conditions; // Store condition names or codes for lightweight storage

    public Patient() {
    }

    public Patient(Long id, String name, LocalDate birthdate, Long userId, List<String> conditions) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.userId = userId;
        this.conditions = conditions;
    }

    // Getters and Setters
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
}

