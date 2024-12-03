package com.example.repository;

import com.example.model.Encounter;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EncounterRepository implements PanacheRepository<Encounter> {
}
