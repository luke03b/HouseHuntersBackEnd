package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Immagini;
import com.househuntersbackend.demo.repositories.ImmaginiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImmaginiService {
    private final ImmaginiRepository immaginiRepository;

    public ImmaginiService(ImmaginiRepository immaginiRepository) {
        this.immaginiRepository = immaginiRepository;
    }

    public Immagini createImmagini(Immagini immagini) {
        return immaginiRepository.save(immagini);
    }

    public List<Immagini> getAllImmaginiByIdAnnuncio(UUID idAnnuncio){
        return immaginiRepository.findAllByAnnuncio_Id(idAnnuncio);
    }
}
