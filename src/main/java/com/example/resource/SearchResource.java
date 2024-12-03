package com.example.resource;

import jakarta.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.example.model.Condition;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Practitioner;
import com.example.service.SearchService;
import io.smallrye.mutiny.Uni;
import java.util.List;

@Path("/search")
public class SearchResource {

    @Inject
    SearchService searchService;

    // Endpoint to search patients by name
    @GET
    @Path("/patients/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Patient>> getPatientsByName(@PathParam("name") String name) {
        return searchService.searchPatientsByName(name);
    }

    // Endpoint to search conditions by patient ID
    @GET
    @Path("/conditions/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Condition>> getConditionsByPatientId(@PathParam("patientId") Long patientId) {
        return searchService.searchConditionsByPatientId(patientId);
    }

    // Endpoint to search conditions by status
    @GET
    @Path("/conditions/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Condition>> getConditionsByStatus(@PathParam("status") String status) {
        return searchService.searchConditionsByStatus(status);
    }

    // Endpoint to search practitioners by specialization
    @GET
    @Path("/practitioners/specialization/{specialization}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Practitioner>> getPractitionersBySpecialization(@PathParam("specialization") String specialization) {
        return searchService.searchPractitionersBySpecialization(specialization);
    }

    // Endpoint to search practitioners with encounters on a specific date
    @GET
    @Path("/practitioners/encounters/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Practitioner>> getPractitionersWithEncountersByDate(@PathParam("date") String date) {
        return searchService.searchPractitionersWithEncountersByDate(date);
    }

    // Endpoint to search encounters by patient ID
    @GET
    @Path("/encounters/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Encounter>> getEncountersByPatientId(@PathParam("patientId") Long patientId) {
        return searchService.searchEncountersByPatientId(patientId);
    }

    // Endpoint to search encounters by practitioner ID
    @GET
    @Path("/encounters/practitioner/{practitionerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Encounter>> getEncountersByPractitionerId(@PathParam("practitionerId") Long practitionerId) {
        return searchService.searchEncountersByPractitionerId(practitionerId);
    }

    // Endpoint to search encounters by date
    @GET
    @Path("/encounters/date/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Encounter>> getEncountersByDate(@PathParam("date") String date) {
        return searchService.searchEncountersByDate(date);
    }

    @GET
    @Path("/patients/by-condition")
    public Uni<List<Patient>> searchPatientsByCondition(@QueryParam("conditionName") String conditionName) {
        return searchService.searchPatientsByCondition(conditionName);
    }

    @GET
    @Path("/patients-by-encounter")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Uni<Patient>>> getPatientsByEncounter(
            @QueryParam("reason") String reason,
            @QueryParam("outcome") String outcome,
            @QueryParam("date") String encounterDate) {

        // Call the method from the service and return the response
        return searchService.searchPatientsByEncounter(reason, outcome, encounterDate);
    }


}

