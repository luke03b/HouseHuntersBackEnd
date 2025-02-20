package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferteService {
    @Autowired
    private OfferteRepository offerteRepository;
    public Offerte createOfferte(Offerte offerte) {
        return offerteRepository.save(offerte);
    }
}
