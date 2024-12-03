package com.example.model;

import jakarta.persistence.*;

@Entity
public class Practitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String specialization;

    @Enumerated(EnumType.STRING)
    private Role role; // Assuming Role is an enum like DOCTOR, NURSE, etc.

    private Long userId; // Reference to associated user

    public Practitioner() {
    }

    public Practitioner(Long id, String name, String specialization, Role role, Long userId) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.role = role;
        this.userId = userId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
