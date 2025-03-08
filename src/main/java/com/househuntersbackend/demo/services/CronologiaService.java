package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Cronologia;
import com.househuntersbackend.demo.repositories.CronologiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CronologiaService {

    private final CronologiaRepository cronologiaRepository;

    public CronologiaService(CronologiaRepository cronologiaRepository) {
        this.cronologiaRepository = cronologiaRepository;
    }

    public Cronologia createCronologia(Cronologia cronologia) {
        cronologia.setDataVisualizzazione(LocalDateTime.now());
        return cronologiaRepository.save(cronologia);
    }
}
