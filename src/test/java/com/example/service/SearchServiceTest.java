package com.example.service;


import com.example.DTO.EncounterDTO;
import com.example.DTO.PatientDTO;
import com.example.model.Condition;
import com.example.model.ConditionStatus;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.repository.ConditionRepository;
import com.example.repository.EncounterRepository;
import com.example.repository.PatientRepository;
import com.example.repository.PractitionerRepository;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    SearchService searchService;

    @Mock
    EncounterRepository encounterRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchPatientsByName() {
        // Arrange: Mock the repository behavior
        String searchName = "John Doe";
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName(searchName);
        patient.setConditions(List.of());

        // Create a mock PanacheQuery
        PanacheQuery<Patient> mockQuery = mock(PanacheQuery.class);

        // Mock the behavior of the PanacheQuery to return a Uni with the list of patients
        when(mockQuery.list()).thenReturn(Uni.createFrom().item(List.of(patient)));  // Correctly wrap the result in Uni

        // Mock the repository to return the PanacheQuery
        when(patientRepository.find("name", searchName)).thenReturn(mockQuery);

        // Act: Call the method to test
        Uni<List<PatientDTO>> result = searchService.searchPatientsByName(searchName);

        // Assert: Verify the result
        result.subscribe().with(patients -> {
            assertNotNull(patients);
            assertEquals(1, patients.size());
            assertEquals(searchName, patients.get(0).getName());
        });

        // Verify the interaction with the repository
        verify(patientRepository, times(1)).find("name", searchName);
    }

    @Test
    public void testSearchPatientsByCondition() {
        // Arrange: Mock the repository behavior
        String conditionName = "Diabetes";

        // Using constructor to create the Condition and Patient objects
        Condition condition = new Condition(1L, "Diabetes", "Chronic disease", LocalDate.now(), ConditionStatus.ACTIVE, null);
        Patient patient = new Patient(1L, "John Doe", LocalDate.of(1990, 1, 1), 1L, List.of(condition));

        // Create a mock PanacheQuery
        PanacheQuery<Patient> mockQuery = mock(PanacheQuery.class);

        // Mock the behavior of the PanacheQuery to return a Uni with the list of patients
        when(mockQuery.list()).thenReturn(Uni.createFrom().item(List.of(patient)));

        // Mock the repository to return the PanacheQuery
        when(patientRepository.findAll()).thenReturn(mockQuery);

        // Act: Call the method to test
        Uni<List<PatientDTO>> result = searchService.searchPatientsByCondition(conditionName);

        // Assert: Verify the result
        result.subscribe().with(patients -> {
            assertNotNull(patients);
            assertEquals(1, patients.size());
            assertEquals("John Doe", patients.get(0).getName());
        });

        // Verify the interaction with the repository
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testGetPractitionerEncountersByDate() {
        // Arrange: Mock the repository behavior
        Long practitionerId = 1L;
        String date = "2024-12-25";

        // Using constructor to create the Encounter object
        Encounter encounter = new Encounter(1L, LocalDateTime.parse(date + "T00:00:00"), "Consultation", "Successful", 1L, practitionerId);

        // Create a mock PanacheQuery for encounters
        PanacheQuery<Encounter> mockEncounterQuery = mock(PanacheQuery.class);

        PanacheQuery<Patient> mockPatientQuery = mock(PanacheQuery.class);

        // Mock the behavior of the PanacheQuery to return a Uni with the list of encounters
        when(mockEncounterQuery.list()).thenReturn(Uni.createFrom().item(List.of(encounter)));


        // Mock the repository to return the PanacheQuery for encounter search
        when(encounterRepository.find("practitionerId = ?1 and date >= ?2", practitionerId, LocalDate.parse(date).atStartOfDay()))
                .thenReturn(mockEncounterQuery);

        // Mock patient repository to return patient info
        Patient patient = new Patient(1L, "John Doe", LocalDate.of(1990, 1, 1), 1L, Collections.emptyList());
        when(mockPatientQuery.list()).thenReturn(Uni.createFrom().item(List.of(patient)));
        when(patientRepository.find("id in ?1", Collections.singleton(1L)))
                .thenReturn(mockPatientQuery);

        // Act: Call the method to test
        Uni<List<EncounterDTO>> result = searchService.getPractitionerEncountersByDate(practitionerId, date);

        // Assert: Verify the result
        result.subscribe().with(encounters -> {
            assertNotNull(encounters);
            assertEquals(1, encounters.size());
            assertEquals("John Doe", encounters.get(0).getPatientName());  // Ensure patient name is correctly mapped
        });

        // Verify the interaction with the repository
        verify(encounterRepository, times(1))
                .find("practitionerId = ?1 and date >= ?2", practitionerId, LocalDate.parse(date).atStartOfDay());
        verify(patientRepository, times(1)).find("id in ?1", Collections.singleton(1L));  // Ensure patient info is fetched
    }


    @Test
    public void testGetPatientsByPractitioner() {
        // Arrange: Mock the repository behavior
        Long practitionerId = 1L;

        // Using constructor to create the Encounter object
        Encounter encounter = new Encounter(1L, LocalDateTime.now(), "Consultation", "Successful", 1L, practitionerId);

        // Create a mock PanacheQuery
        PanacheQuery<Encounter> mockEncounterQuery = mock(PanacheQuery.class);

        PanacheQuery<Patient> mockPatientQuery = mock(PanacheQuery.class);

        // Mock the behavior of the PanacheQuery to return a Uni with the list of encounters
        when(mockEncounterQuery.list()).thenReturn(Uni.createFrom().item(List.of(encounter)));

        // Mock the repository to return the PanacheQuery
        when(encounterRepository.find("practitionerId", practitionerId)).thenReturn(mockEncounterQuery);

        // Mock patient repository to return patient info
        Patient patient = new Patient(1L, "John Doe", LocalDate.of(1990, 1, 1), 1L, Collections.emptyList());
        when(mockPatientQuery.list()).thenReturn(Uni.createFrom().item(List.of(patient)));
        when(patientRepository.find("id in ?1", Collections.singleton(1L))).thenReturn(mockPatientQuery);

        // Act: Call the method to test
        Uni<List<PatientDTO>> result = searchService.getPatientsByPractitioner(practitionerId);

        // Assert: Verify the result
        result.subscribe().with(patients -> {
            assertNotNull(patients);
            assertEquals(1, patients.size());
            assertEquals("John Doe", patients.get(0).getName());
        });

        // Verify the interaction with the repository
        verify(encounterRepository, times(1)).find("practitionerId", practitionerId);
    }
}

