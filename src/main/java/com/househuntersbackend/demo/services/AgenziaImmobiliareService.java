package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.AgenziaImmobiliare;
import com.househuntersbackend.demo.repositories.AgenziaImmobiliareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenziaImmobiliareService {
    private final AgenziaImmobiliareRepository agenziaImmobiliareRepository;

    public AgenziaImmobiliareService(AgenziaImmobiliareRepository agenziaImmobiliareRepository) {
        this.agenziaImmobiliareRepository = agenziaImmobiliareRepository;
    }

    public AgenziaImmobiliare createAgenziaImmobiliare(AgenziaImmobiliare agenziaImmobiliare) {
        if (agenziaImmobiliare.getNome() == null || agenziaImmobiliare.getPartitaiva() == null) {
            throw new IllegalArgumentException("Nome e Partita IVA non possono essere nulli");
        }
        return agenziaImmobiliareRepository.save(agenziaImmobiliare);
    }

    public List<AgenziaImmobiliare> getAllAgenziaImmobiliare() {
        return agenziaImmobiliareRepository.findAll();
    }
}