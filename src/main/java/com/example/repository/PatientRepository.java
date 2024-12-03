package com.example.repository;

import com.example.model.Patient;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {

}

