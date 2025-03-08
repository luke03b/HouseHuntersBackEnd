package com.househuntersbackend.demo.repositories;

import com.househuntersbackend.demo.entities.AgenziaImmobiliare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgenziaImmobiliareRepository extends JpaRepository<AgenziaImmobiliare, UUID> {
}
