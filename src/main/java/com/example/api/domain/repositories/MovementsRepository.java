package com.example.api.domain.repositories;

import com.example.api.domain.entities.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementsRepository extends JpaRepository<Movements, Long> {
}
