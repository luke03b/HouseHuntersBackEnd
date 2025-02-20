package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Cronologia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CronologiaRepository extends JpaRepository<Cronologia, UUID> {
}
