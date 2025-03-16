package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.enumerations.StatoVisita;
import com.househuntersbackend.demo.exceptions.*;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.utils.VisitaUtils;
import com.househuntersbackend.demo.verifiers.VisitaVerifier;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisiteService {
    private final VisiteRepository visiteRepository;
    private final VisitaVerifier visitaVerifier;

    public VisiteService(VisiteRepository visiteRepository, VisitaVerifier visitaVerifier) {
        this.visiteRepository = visiteRepository;
        this.visitaVerifier = visitaVerifier;
    }

    public Visite createVisite(Visite visite) throws VisitaNonValidaException {
        try {
            visitaVerifier.isVisitaEsistente(visite);
            visite.setStato(StatoVisita.IN_ATTESA);
            visitaVerifier.areAttributiVisitaValidi(visite.getData(), visite.getOrarioInizio(), visite.getOrarioFine());
            return visiteRepository.save(visite);
        } catch (VisitaNonValidaException e) {
            throw new VisitaNonValidaException(e.getMessage());
        }
    }

    public List<Visite> getVisiteAnnuncio(UUID idAnnuncio) {
        return visiteRepository.findByAnnuncioId(idAnnuncio);
    }

    public List<Visite> getTutteVisiteCliente(UUID idCliente) {
        return visiteRepository.findByClienteId(idCliente);
    }

    public List<Visite> getVisiteByStatoOnAnnuncio(UUID idAnnuncio, String stato){
        return visiteRepository.findVisiteByAnnuncioIdAndStato(idAnnuncio, VisitaUtils.mappaStatoVisita(stato));
    }

    public void updateStatoVisite(Visite visite, String stato) {
        Optional<Visite> existingVisita = visiteRepository.findById(visite.getId());

        if (existingVisita.isPresent()) {
            Visite visitaToUpdate = existingVisita.get();

            visitaToUpdate.setStato(VisitaUtils.mappaStatoVisita(stato));

            visiteRepository.save(visitaToUpdate);
        } else {
            throw new EntityNotFoundException("Offerta non trovata con ID: " + visite.getId());
        }
    }

    public List<Visite> getTutteVisiteConStatoByAgente(String stato, String subAgente){
        return visiteRepository.findVisiteByStatoAndAgente(VisitaUtils.mappaStatoVisita(stato), subAgente);
    }
}
