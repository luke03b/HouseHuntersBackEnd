package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OfferteRepository extends JpaRepository<Offerte, UUID> {
    @Query(value = "SELECT * FROM offerte WHERE id_annuncio = :idAnnuncio", nativeQuery = true)
    List<Offerte> findOfferteByAnnuncioId(@Param("idAnnuncio") UUID idAnnuncio);

    boolean existsByAnnuncioAndClienteAndStato(Annunci annuncio, Users cliente, StatoOfferta stato);

    @Query("SELECT o FROM Offerte o WHERE o.cliente.id = :idCliente")
    List<Offerte> findOfferteConAnnuncioByClienteId(@Param("idCliente") UUID idCliente);

}
