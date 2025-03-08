package com.househuntersbackend.demo.repositories;

import com.househuntersbackend.demo.entities.Cronologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CronologiaRepository extends JpaRepository<Cronologia, UUID> {
    @Modifying
    @Query("DELETE FROM Cronologia c WHERE c.cliente.id = :userId")
    void deleteByClienteId(@Param("userId") UUID userId);

}
