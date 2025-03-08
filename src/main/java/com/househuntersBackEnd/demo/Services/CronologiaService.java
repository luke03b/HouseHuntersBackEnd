package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Cronologia;
import com.househuntersBackEnd.demo.Repositories.CronologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
