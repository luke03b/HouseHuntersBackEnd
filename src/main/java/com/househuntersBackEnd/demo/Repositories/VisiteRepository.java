package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Visite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisiteRepository extends JpaRepository<Visite, UUID> {
    List<Visite> findByAnnuncioId(UUID annuncioId);
    List<Visite> findByClienteId(UUID clienteId);
}
