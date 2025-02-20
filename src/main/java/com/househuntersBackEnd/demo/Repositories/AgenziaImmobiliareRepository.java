package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.AgenziaImmobiliare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgenziaImmobiliareRepository extends JpaRepository<AgenziaImmobiliare, UUID> {
}
