package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Repositories.VisiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisiteService {
    @Autowired
    private VisiteRepository visiteRepository;
    public Visite createVisite(Visite visite) throws VisitaInAttesaEsistenteException{
        boolean esisteInAttesa = visiteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                visite.getAnnuncio(), visite.getCliente(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA
        );
        if (esisteInAttesa) {
            throw new VisitaInAttesaEsistenteException("L'utente ha già una visita in attesa per questo annuncio.");
        }
        visite.setOrarioFine(visite.getOrarioInizio().plusHours(1));
        visite.setStato(StatoVisita.IN_ATTESA);
        return visiteRepository.save(visite);
    }
    public List<Visite> getVisiteAnnuncio(UUID idAnnuncio) {
        return visiteRepository.findByAnnuncioId(idAnnuncio);
    }
    public List<Visite> getTutteVisiteCliente(UUID idCliente) {
        return visiteRepository.findByClienteId(idCliente);
    }
    public List<Visite> getVisiteByStatoOnAnnuncio(UUID idAnnuncio, String stato){
        StatoVisita statoFormattato;
        if(stato.equals(StatoVisita.RIFIUTATA.toString())) {
            statoFormattato = StatoVisita.RIFIUTATA;
        } else if (stato.equals(StatoVisita.IN_ATTESA.toString())){
            statoFormattato = StatoVisita.IN_ATTESA;
        } else {
            statoFormattato = StatoVisita.CONFERMATA;
        }

        return visiteRepository.findVisiteByAnnuncioIdAndStato(idAnnuncio, statoFormattato);
    }

    public Visite updateStatoVisite(Visite visite, String stato) {
        Optional<Visite> existingVisita = visiteRepository.findById(visite.getId());

        if (existingVisita.isPresent()) {
            Visite visitaToUpdate = existingVisita.get();

            if(stato.equals(StatoVisita.RIFIUTATA.toString())) {
                visitaToUpdate.setStato(StatoVisita.RIFIUTATA);
            } else if (stato.equals(StatoVisita.IN_ATTESA.toString())){
                visitaToUpdate.setStato(StatoVisita.IN_ATTESA);
            } else {
                visitaToUpdate.setStato(StatoVisita.CONFERMATA);
            }

            // Salva e restituisci l'entità aggiornata
            return visiteRepository.save(visitaToUpdate);
        } else {
            throw new EntityNotFoundException("Offerta non trovata con ID: " + visite.getId());
        }
    }

    public List<Visite> getTutteVisiteConStatoByAgente(String stato, String subAgente){
        StatoVisita statoFormattato;
        if(stato.equals(StatoVisita.RIFIUTATA.toString())) {
            statoFormattato = StatoVisita.RIFIUTATA;
        } else if (stato.equals(StatoVisita.IN_ATTESA.toString())){
            statoFormattato = StatoVisita.IN_ATTESA;
        } else {
            statoFormattato = StatoVisita.CONFERMATA;
        }

        return visiteRepository.findVisiteByStatoAndAgente(statoFormattato, subAgente);
    }
}
