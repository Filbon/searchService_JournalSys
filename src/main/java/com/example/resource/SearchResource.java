package com.example.resource;

import com.example.DTO.EncounterDTO;
import com.example.DTO.PatientDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.example.model.Patient;
import com.example.model.Practitioner;
import com.example.service.SearchService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/search")
public class SearchResource {

    @Inject
    SearchService searchService;

    // Endpoint to search patients by name
    @GET
    @Path("/patients/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDTO>> getPatientsByName(@PathParam("name") String name) {
        System.out.println("Received request to search patients by name: " + name);

        return searchService.searchPatientsByName(name)
                .onItem().invoke(results -> {
                    if (results != null && !results.isEmpty()) {
                        System.out.println("Found patients: " + results.get(0).getName());
                    } else {
                        System.out.println("No patients found for the name: " + name);
                    }
                })
                .onFailure().invoke(throwable -> {
                    System.err.println("An error occurred while searching for patients by name: " + throwable.getMessage());
                    throwable.printStackTrace();
                });
    }

    @GET
    @Path("/patients/by-condition")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDTO>> searchPatientsByCondition(@QueryParam("conditionName") String conditionName) {
        return searchService.searchPatientsByCondition(conditionName);
    }

    @GET
    @Path("/{practitionerId}/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDTO>> getPatientsByPractitioner(@PathParam("practitionerId") Long practitionerId) {
        System.out.println("Received request to get patients by practitioner ID: " + practitionerId);

        return searchService.getPatientsByPractitioner(practitionerId);
    }
    @GET
    @Path("/{practitionerId}/encounters/date/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<EncounterDTO>> getPractitionerEncountersByDate(
            @PathParam("practitionerId") Long practitionerId,
            @PathParam("date") String date) {
        System.out.println("Received request to get encounters for practitioner ID: " + practitionerId + " on date: " + date);

        return searchService.getPractitionerEncountersByDate(practitionerId, date)
                .onItem().invoke(results -> {
                    if (results != null && !results.isEmpty()) {
                        System.out.println("Found encounters for practitioner ID " + practitionerId + " on date: " + date);
                    } else {
                        System.out.println("No encounters found for practitioner ID " + practitionerId + " on date: " + date);
                    }
                })
                .onFailure().invoke(throwable -> {
                    System.err.println("An error occurred while searching for encounters by practitioner ID and date: " + throwable.getMessage());
                    throwable.printStackTrace();
                });
    }
}

