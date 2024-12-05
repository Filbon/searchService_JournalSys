package com.example.service;

import com.example.DTO.EncounterDTO;
import com.example.DTO.PatientDTO;
import com.example.Mapper.EntityMapper;
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
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    public Uni<List<PatientDTO>> searchPatientsByName(String name) {
        return patientRepository.find("name", name).list()
                .onItem().transform(patients ->
                        patients.stream()
                                .map(EntityMapper::mapToPatientDTO)
                                .collect(Collectors.toList())
                );
    }

    @WithSession
    public Uni<List<PatientDTO>> searchPatientsByCondition(String conditionName) {
        return patientRepository.findAll().list()
                .onItem().transform(patients ->
                        patients.stream()
                                .filter(patient -> patient.getConditions().stream()
                                        .anyMatch(condition -> condition.getName().equalsIgnoreCase(conditionName)))
                                .map(EntityMapper::mapToPatientDTO)
                                .collect(Collectors.toList())
                );
    }
    @WithSession
    public Uni<List<EncounterDTO>> getPractitionerEncountersByDate(Long practitionerId, String date) {

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime parsedDate = localDate.atStartOfDay();

        return encounterRepository.find("practitionerId = ?1 and date >= ?2",
                        practitionerId, parsedDate).list()
                .onItem().transform(encounters ->
                        encounters.stream()
                                .map(EntityMapper::mapToEncounterDTO)
                                .collect(Collectors.toList())
                );
    }

    @WithSession
    public Uni<List<PatientDTO>> getPatientsByPractitioner(Long practitionerId) {
        return encounterRepository.find("practitionerId", practitionerId).list()
                .onItem().invoke(encounters -> {
                    System.out.println("Encounter list fetched. Size: " + encounters.size());
                })
                .onItem().transformToUni(encounters -> {
                    if (encounters.isEmpty()) {
                        System.out.println("No encounters found for practitionerId: " + practitionerId);
                        return Uni.createFrom().item(Collections.emptyList());
                    }

                    Set<Long> patientIds = encounters.stream()
                            .map(Encounter::getPatientId)
                            .collect(Collectors.toSet ());
                    System.out.println("Extracted patient IDs: " + patientIds);
                    patientIds.forEach(System.out::println);

                    // Explicitly specifying the return type
                    return patientRepository.find("id in ?1", patientIds)
                            .list()
                            .map(patients -> {
                                if (patients != null && !patients.isEmpty()) {
                                    System.out.println("Fetched patients. Size: " + patients.size());
                                    System.out.println("Patients found: " + patients.stream().map(Patient::getId).toList());
                                } else {
                                    System.out.println("No patients were found for the given IDs.");
                                }
                                assert patients != null;
                                return patients.stream()
                                        .map(EntityMapper::mapToPatientDTO)
                                        .collect(Collectors.toList());
                            });
                });
    }



}
