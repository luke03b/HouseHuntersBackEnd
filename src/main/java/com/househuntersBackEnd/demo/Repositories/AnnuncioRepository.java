package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Enumerations.StatoAnnuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AnnuncioRepository extends JpaRepository<Annunci, UUID>, JpaSpecificationExecutor<Annunci> {
    List<Annunci> findByAgenteSub(String sub);

    @Query(value = """
        SELECT a.* FROM annunci a
        JOIN (
            SELECT DISTINCT ON (c.id_annuncio) c.id_annuncio, c.data_visualizzazione 
            FROM cronologia c
            JOIN users u ON c.id_cliente = u.id
            WHERE u.id = CAST(:idCliente AS UUID) AND u.tipo = 'CLIENTE'
            ORDER BY c.id_annuncio, c.data_visualizzazione DESC
        ) latest ON a.id = latest.id_annuncio
        ORDER BY latest.data_visualizzazione DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Annunci> findAnnunciRecentementeVisualizzatiByIdCliente(@Param("idCliente") String idCliente);

    @Query("""
    SELECT DISTINCT a FROM Annunci a
    LEFT JOIN Offerte o ON a.id = o.annuncio.id
    LEFT JOIN Visite v ON a.id = v.annuncio.id AND v.stato = 'IN_ATTESA'
    WHERE a.agente.sub = :sub
    AND (
        (:offerte = true AND :prenotazioni = true AND o.id IS NOT NULL AND v.id IS NOT NULL) OR
        (:offerte = true AND :prenotazioni = false AND o.id IS NOT NULL) OR
        (:prenotazioni = true AND :offerte = false AND v.id IS NOT NULL) OR
        (:offerte = false AND :prenotazioni = false)
    )
    AND (:disponibili = false OR a.stato = 'DISPONIBILE')
""")
    List<Annunci> findAnnunciConOffertePrenotazioniByAgenteSub(
            @Param("sub") String sub,
            @Param("offerte") boolean offerte,
            @Param("prenotazioni") boolean prenotazioni,
            @Param("disponibili") boolean disponibili
    );


    @Modifying
    @Query("UPDATE Annunci a SET a.stato = :stato WHERE a.id = :idAnnuncio")
    void updateStatoAnnuncio(@Param("idAnnuncio") UUID idAnnuncio, @Param("stato") StatoAnnuncio stato);


    List<Annunci> getAnnuncioById(UUID id);
}
