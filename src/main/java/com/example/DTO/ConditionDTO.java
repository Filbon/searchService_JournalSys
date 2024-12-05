package com.example.DTO;

import java.time.LocalDate;

public class ConditionDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate diagnosisDate;
    private String status;

    public ConditionDTO() {}

    public ConditionDTO(Long id, String name, String description, LocalDate diagnosisDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

