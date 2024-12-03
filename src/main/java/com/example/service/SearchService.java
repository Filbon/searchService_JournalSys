package com.example.service;

import javax.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.model.*;
import com.example.repository.ConditionRepository;
import com.example.repository.EncounterRepository;
import com.example.repository.PatientRepository;
import com.example.repository.PractitionerRepository;
import io.smallrye.mutiny.Uni;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchService {

    @Inject
    PatientRepository patientRepository;

    @Inject
    ConditionRepository conditionRepository;

    @Inject
    PractitionerRepository practitionerRepository;

    @Inject
    EncounterRepository encounterRepository;

    // Search for patients by name
    public Uni<List<Patient>> searchPatientsByName(String name) {
        return patientRepository.find("name", name).list();
    }

    // Search for conditions by patient ID
    public Uni<List<Condition>> searchConditionsByPatientId(Long patientId) {
        return conditionRepository.find("patient.id", patientId).list();
    }

    // Search for conditions by status
    public Uni<List<Condition>> searchConditionsByStatus(String status) {
        return conditionRepository.find("status", ConditionStatus.valueOf(status)).list();
    }

    // Search for practitioners by specialization
    public Uni<List<Practitioner>> searchPractitionersBySpecialization(String specialization) {
        return practitionerRepository.find("specialization", specialization).list();
    }

    // Search for practitioners with encounters on a specific date
    public Uni<List<Practitioner>> searchPractitionersWithEncountersByDate(String date) {
        return practitionerRepository.find("encounters.date", LocalDateTime.parse(date)).list();
    }

    // Search for encounters by patient ID
    public Uni<List<Encounter>> searchEncountersByPatientId(Long patientId) {
        return encounterRepository.find("patient.id", patientId).list();
    }

    // Search for encounters by practitioner ID
    public Uni<List<Encounter>> searchEncountersByPractitionerId(Long practitionerId) {
        return encounterRepository.find("practitioner.id", practitionerId).list();
    }

    // Search for encounters by date
    public Uni<List<Encounter>> searchEncountersByDate(String date) {
        return encounterRepository.find("date", LocalDateTime.parse(date)).list();
    }

    public Uni<List<Patient>> searchPatientsByCondition(String conditionName) {
        // Find patients who have conditions matching the given condition name reactively
        return patientRepository.findAll().list()
                .onItem().transform(patients ->
                        patients.stream()
                                .filter(patient -> patient.getConditions().stream()
                                        .anyMatch(condition -> condition.equalsIgnoreCase(conditionName)))  // Check if condition matches
                                .distinct()  // Ensure no duplicates if patients have multiple conditions
                                .collect(Collectors.toList())
                );
    }

    public Uni<List<Uni<Patient>>> searchPatientsByEncounter(String reason, String outcome, String encounterDate) {
        return encounterRepository.findAll().list()
                .onItem().transform(encounters ->
                        encounters.stream()
                                .filter(encounter -> encounter.getReason().equalsIgnoreCase(reason) &&
                                        encounter.getOutcome().equalsIgnoreCase(outcome) &&
                                        encounter.getDate().toLocalDate().toString().equals(encounterDate)) // Match the encounter date
                                .map(Encounter::getPatientId)  // Get patient IDs from encounters
                                .distinct()  // Ensure unique patient IDs
                                .map(patientId -> patientRepository.findById(patientId))  // Fetch patients by ID
                                .filter(Objects::nonNull)  // Exclude any null results if not found
                                .collect(Collectors.toList())
                );
    }
}
