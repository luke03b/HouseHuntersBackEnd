package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisiteRepository extends JpaRepository<Visite, UUID> {
    List<Visite> findByAnnuncioId(UUID annuncioId);
    List<Visite> findByClienteId(UUID clienteId);
    boolean existsByAnnuncioAndClienteAndStatoOrStato(Annunci annuncio, Users cliente, StatoVisita stato, StatoVisita stato2);
}
