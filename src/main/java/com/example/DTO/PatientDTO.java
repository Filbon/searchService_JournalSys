package com.example.DTO;

import java.time.LocalDate;
import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private List<ConditionDTO> conditions;

    public PatientDTO() {}

    public PatientDTO(Long id, String name, LocalDate birthdate, List<ConditionDTO> conditions) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.conditions = conditions;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<ConditionDTO> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionDTO> conditions) {
        this.conditions = conditions;
    }
}

