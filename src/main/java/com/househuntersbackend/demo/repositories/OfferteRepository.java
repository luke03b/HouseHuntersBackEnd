package com.househuntersbackend.demo.repositories;

import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.Offerte;
import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.enumerations.StatoOfferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OfferteRepository extends JpaRepository<Offerte, UUID> {
    @Query(value = "SELECT * FROM offerte WHERE id_annuncio = :idAnnuncio", nativeQuery = true)
    List<Offerte> findOfferteByAnnuncioId(@Param("idAnnuncio") UUID idAnnuncio);

    boolean existsByAnnuncioAndClienteAndStatoOrStato(Annunci annuncio, Users cliente, StatoOfferta stato, StatoOfferta stato2);

    @Query("SELECT o FROM Offerte o WHERE o.cliente.id = :idCliente")
    List<Offerte> findOfferteConAnnuncioByClienteId(@Param("idCliente") UUID idCliente);

    List<Offerte> findOfferteByAnnuncioIdAndStato(UUID idAnnuncio, StatoOfferta statoFormattato);

    @Modifying
    @Query("UPDATE Offerte o SET o.stato = :stato WHERE o.annuncio.id = :idAnnuncio AND o.id <> :idOfferta")
    void updateStatoOfferteEscluse(@Param("idAnnuncio") UUID idAnnuncio, @Param("idOfferta") UUID idOfferta, @Param("stato") StatoOfferta stato);

    @Modifying
    @Query("UPDATE Offerte o SET o.cliente = NULL, o.nomeOfferente = :nome, o.cognomeOfferente = :cognome, o.emailOfferente = :email WHERE o.cliente.id = :userId AND o.stato = 'ACCETTATA'")
    void updateAcceptedOffers(@Param("userId") UUID userId, @Param("nome") String nome, @Param("cognome") String cognome, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Offerte o WHERE o.cliente.id = :userId AND o.stato <> 'ACCETTATA'")
    void deleteNotAcceptedOffers(@Param("userId") UUID userId);

    boolean existsByAnnuncioAndStato(Annunci annuncio, StatoOfferta stato);
}
