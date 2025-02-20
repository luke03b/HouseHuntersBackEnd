package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Repositories.VisiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisiteService {
    @Autowired
    private VisiteRepository visiteRepository;
    public Visite createVisite(Visite visite) {
        return visiteRepository.save(visite);
    }
}
