package com.example.repository;

import com.example.model.Condition;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ConditionRepository implements PanacheRepository<Condition> {
    public Uni<List<Condition>> findByName(String name) {
        return find("name", name).list();  // This will return a Uni<List<Condition>>
    }
}

