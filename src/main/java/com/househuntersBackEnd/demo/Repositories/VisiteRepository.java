package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface VisiteRepository extends JpaRepository<Visite, UUID> {
    List<Visite> findByAnnuncioId(UUID annuncioId);
    List<Visite> findByClienteId(UUID clienteId);
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN TRUE ELSE FALSE END FROM Visite v " +
            "WHERE v.cliente = :cliente " +
            "AND (v.stato = :stato OR v.stato = :stato1) " +
            "AND v.annuncio = :annuncio")
    boolean existsByAnnuncioAndClienteAndStatoOrStato(@Param("cliente") Users cliente,
                                               @Param("stato") StatoVisita stato,
                                               @Param("stato1") StatoVisita stato1,
                                               @Param("annuncio") Annunci annuncio);


    List<Visite> findVisiteByAnnuncioIdAndStato(UUID idAnnuncio, StatoVisita statoFormattato);
    @Query("SELECT v FROM Visite v WHERE v.stato = :stato AND v.annuncio.agente.sub = :subAgente")
    List<Visite> findVisiteByStatoAndAgente(@Param("stato") StatoVisita stato, @Param("subAgente") String subAgente);
    @Modifying
    @Query("UPDATE Visite v SET v.stato = :stato WHERE v.annuncio.id = :idAnnuncio AND v.cliente.id <> :idCliente")
    void updateStatoVisiteEscluse(@Param("idAnnuncio") UUID idAnnuncio, @Param("idCliente") UUID idCliente, @Param("stato") StatoVisita stato);
    @Modifying
    @Query("UPDATE Visite v SET v.stato = :stato WHERE v.annuncio.id = :idAnnuncio")
    void updateStatoVisitePerAnnuncio(@Param("idAnnuncio") UUID idAnnuncio, @Param("stato") StatoVisita stato);

    @Modifying
    @Query("DELETE FROM Visite v WHERE v.cliente.id = :userId")
    void deleteByClienteId(@Param("userId") UUID userId);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN TRUE ELSE FALSE END FROM Visite v " +
            "WHERE v.annuncio = :annuncio " +
            "AND (v.stato = :stato1 OR v.stato = :stato2) " +
            "AND v.data = :data " +
            "AND v.orarioInizio = :orarioInizio")
    boolean existsByAnnuncioAndStatoOrStatoAndDataAndOrarioInizio(@Param("annuncio") Annunci annuncio,
                                       @Param("stato1") StatoVisita stato1,
                                       @Param("stato2") StatoVisita stato2,
                                       @Param("data") LocalDate data,
                                       @Param("orarioInizio") LocalTime orarioInizio);


}
