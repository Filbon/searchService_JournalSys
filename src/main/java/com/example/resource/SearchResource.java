package com.example.resource;

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
    public Uni<List<Patient>> getPatientsByName(@PathParam("name") String name) {
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



    // Endpoint to search practitioners with encounters on a specific date
    @GET
    @Path("/practitioners/encounters/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Practitioner>> getPractitionersWithEncountersByDate(@PathParam("date") String date) {
        return searchService.searchPractitionersWithEncountersByDate(date);
    }

    /*@GET
    @Path("/patients/by-condition")
    public Uni<List<Patient>> searchPatientsByCondition(@QueryParam("conditionName") String conditionName) {
        return searchService.searchPatientsByCondition(conditionName);
    }*/

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

    @GET
    @Path("/test")
    public String testEndpoint() {
        System.out.println("Test endpoint called");
        return "Hello from test endpoint!";
    }

}

