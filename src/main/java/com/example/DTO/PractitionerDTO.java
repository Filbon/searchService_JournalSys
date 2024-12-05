package com.example.DTO;

public class PractitionerDTO {
    private Long id;
    private String name;
    private String specialization;
    private String role; // Assuming Role is an enum, converting it to String
    private Long userId;

    public PractitionerDTO() {}

    public PractitionerDTO(Long id, String name, String specialization, String role, Long userId) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.role = role;
        this.userId = userId;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

