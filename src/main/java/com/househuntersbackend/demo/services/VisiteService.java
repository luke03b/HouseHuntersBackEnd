package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.enumerations.StatoVisita;
import com.househuntersbackend.demo.exceptions.*;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.utils.VisiteUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisiteService {
    private final VisiteRepository visiteRepository;
    private final VisiteUtils visiteUtils;

    public VisiteService(VisiteRepository visiteRepository, VisiteUtils visiteUtils) {
        this.visiteRepository = visiteRepository;
        this.visiteUtils = visiteUtils;
    }

    public Visite createVisite(Visite visite) throws VisitaNonValidaException {
//        boolean esisteInAttesaOAccettataCliente = visiteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
//                 visite.getCliente(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visite.getAnnuncio()
//        );
//
//        if (esisteInAttesaOAccettataCliente) {
//            throw new VisitaInAttesaEsistenteException("L'utente ha già una visita in attesa o confermata per questo annuncio.");
//        }
//
//        boolean esisteInAttesaOAccettataFasciaOraria = visiteRepository.existsByAnnuncioAndStatoOrStatoAndDataAndOrarioInizio(
//                visite.getAnnuncio(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visite.getData(), visite.getOrarioInizio()
//        );
//
//        if(esisteInAttesaOAccettataFasciaOraria) {
//            throw new VisitaInAttesaPerFasciaOrariaEsistenteException("esiste già una visita in attesa o accettata per questo annuncio nella fascia oraria indicata.");
//        }
//
//        boolean esisteOffertaAccettataSuAnnuncio = offerteRepository.existsByAnnuncioAndStato(visite.getAnnuncio(), StatoOfferta.ACCETTATA);
//
//        if(esisteOffertaAccettataSuAnnuncio) {
//            throw new OffertaAccettataEsistenteException("esiste già un'offerta accettata per questo annuncio, quindi non è possibile prenotare visite");
//        }

//        if (visite.getData().isEqual(LocalDate.now())) {
//            throw new DataNonValidaException("non è possibile prenotare visite per il giorno stesso");
//        }

//        if(!VisiteUtils.areAttributiVisitaValidi(visite.getData(), visite.getOrarioInizio(), visite.getOrarioFine())) {
//            throw new VisitaNonValidaException("non è possibile prenotare visite per il giorno stesso o oltre due settimane");
//        }

        try {
            visiteUtils.isVisitaEsistente(visite);
            visite.setOrarioFine(visite.getOrarioInizio().plusHours(1));
            visite.setStato(StatoVisita.IN_ATTESA);
            visiteUtils.areAttributiVisitaValidi(visite.getData(), visite.getOrarioInizio(), visite.getOrarioFine());
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

    public void updateStatoVisite(Visite visite, String stato) {
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

            visiteRepository.save(visitaToUpdate);
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
