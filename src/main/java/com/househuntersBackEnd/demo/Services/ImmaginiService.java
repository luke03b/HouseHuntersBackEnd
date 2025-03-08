package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Immagini;
import com.househuntersBackEnd.demo.Repositories.ImmaginiRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
