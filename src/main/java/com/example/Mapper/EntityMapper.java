package com.example.Mapper;

import com.example.DTO.ConditionDTO;
import com.example.DTO.EncounterDTO;
import com.example.DTO.PatientDTO;
import com.example.DTO.PractitionerDTO;
import com.example.model.Condition;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Practitioner;

import java.util.List;
import java.util.stream.Collectors;

public class EntityMapper {

    public static PatientDTO mapToPatientDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getBirthdate(),
                mapToConditionDTOList(patient.getConditions())
        );
    }

    public static List<ConditionDTO> mapToConditionDTOList(List<Condition> conditions) {
        return conditions.stream()
                .map(EntityMapper::mapToConditionDTO)
                .collect(Collectors.toList());
    }

    public static ConditionDTO mapToConditionDTO(Condition condition) {
        return new ConditionDTO(
                condition.getId(),
                condition.getName(),
                condition.getDescription(),
                condition.getDiagnosisDate(),
                condition.getStatus().name()
        );
    }

    public static EncounterDTO mapToEncounterDTO(Encounter encounter) {
        return new EncounterDTO(
                encounter.getId(),
                encounter.getDate(),
                encounter.getReason(),
                encounter.getOutcome(),
                encounter.getPatientId(),
                encounter.getPractitionerId()
        );
    }

    public static PractitionerDTO mapToPractitionerDTO(Practitioner practitioner) {
        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getName(),
                practitioner.getSpecialization(),
                practitioner.getRole().name(),
                practitioner.getUserId()
        );
    }
}

