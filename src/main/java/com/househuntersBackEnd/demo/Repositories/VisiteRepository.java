package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Visite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VisiteRepository extends JpaRepository<Visite, UUID> {
}
