package com.househuntersbackend.demo.repositories;

import com.househuntersbackend.demo.entities.Immagini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
public interface ImmaginiRepository extends JpaRepository<Immagini, UUID> {
    List<Immagini> findAllByAnnuncio_Id(@Param("idAnnuncio") UUID idAnnuncio);
}
