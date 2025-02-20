package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Annunci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AnnuncioRepository extends JpaRepository<Annunci, UUID>, JpaSpecificationExecutor<Annunci> {
}
