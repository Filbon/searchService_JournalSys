package com.example.repository;

import com.example.model.Practitioner;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PractitionerRepository implements PanacheRepository<Practitioner> {
}
