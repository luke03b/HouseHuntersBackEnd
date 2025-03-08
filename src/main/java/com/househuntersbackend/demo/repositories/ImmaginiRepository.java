package com.househuntersbackend.demo.repositories;

import com.househuntersbackend.demo.entities.Immagini;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface ImmaginiRepository extends JpaRepository<Immagini, UUID> {
}
