package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Immagini;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface ImmaginiRepository extends JpaRepository<Immagini, UUID> {
}
