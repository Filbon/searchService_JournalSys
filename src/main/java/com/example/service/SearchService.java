package com.example.service;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.model.*;
import com.example.repository.ConditionRepository;
import com.example.repository.EncounterRepository;
import com.example.repository.PatientRepository;
import com.example.repository.PractitionerRepository;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;

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
    @WithSession
    public Uni<List<Patient>> searchPatientsByName(String name) {
        Uni<List<Patient>> patient = patientRepository.find("name", name).list();
        System.out.println(patient);
        return patient;
    }


    // Search for practitioners with encounters on a specific date
    @WithSession
    public Uni<List<Practitioner>> searchPractitionersWithEncountersByDate(String date) {
        return practitionerRepository.find("encounters.date", LocalDateTime.parse(date)).list();
    }

    /*@WithSession
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
    }*/
    @WithSession
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
