package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

}
