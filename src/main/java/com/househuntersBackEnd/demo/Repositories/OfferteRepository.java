package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Offerte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfferteRepository extends JpaRepository<Offerte, UUID> {
}
