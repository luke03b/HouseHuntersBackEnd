package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferteService {
    @Autowired
    private OfferteRepository offerteRepository;
    public Offerte createOfferte(Offerte offerte) {
        offerte.setStato(StatoOfferta.IN_ATTESA);
        offerte.setData(LocalDateTime.now());
        return offerteRepository.save(offerte);
    }
}
