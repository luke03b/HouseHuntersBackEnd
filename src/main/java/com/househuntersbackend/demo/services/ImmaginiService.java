package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Immagini;
import com.househuntersbackend.demo.repositories.ImmaginiRepository;
import org.springframework.stereotype.Service;

@Service
public class ImmaginiService {
    private final ImmaginiRepository immaginiRepository;

    public ImmaginiService(ImmaginiRepository immaginiRepository) {
        this.immaginiRepository = immaginiRepository;
    }

    public Immagini createImmagini(Immagini immagini) {
        return immaginiRepository.save(immagini);
    }
}
