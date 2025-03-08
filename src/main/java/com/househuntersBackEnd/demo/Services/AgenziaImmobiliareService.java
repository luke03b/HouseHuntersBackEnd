package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.AgenziaImmobiliare;
import com.househuntersBackEnd.demo.Repositories.AgenziaImmobiliareRepository;
import org.springframework.beans.factory.annotation.Autowired;
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