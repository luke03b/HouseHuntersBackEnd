package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import com.househuntersBackEnd.demo.Exceptions.OffertaAccettataEsistenteException;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaPerFasciaOrariaEsistenteException;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import com.househuntersBackEnd.demo.Repositories.VisiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisiteService {
    private final VisiteRepository visiteRepository;
    private final OfferteRepository offerteRepository;

    public VisiteService(VisiteRepository visiteRepository, OfferteRepository offerteRepository) {
        this.visiteRepository = visiteRepository;
        this.offerteRepository = offerteRepository;
    }

    public Visite createVisite(Visite visite) throws VisitaInAttesaEsistenteException, VisitaInAttesaPerFasciaOrariaEsistenteException, OffertaAccettataEsistenteException {
        boolean esisteInAttesaOAccettataCliente = visiteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                 visite.getCliente(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visite.getAnnuncio()
        );
        if (esisteInAttesaOAccettataCliente) {
            throw new VisitaInAttesaEsistenteException("L'utente ha già una visita in attesa o confermata per questo annuncio.");
        }

        boolean esisteInAttesaOAccettataFasciaOraria = visiteRepository.existsByAnnuncioAndStatoOrStatoAndDataAndOrarioInizio(
                visite.getAnnuncio(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visite.getData(), visite.getOrarioInizio()
        );

        if(esisteInAttesaOAccettataFasciaOraria) {
            throw new VisitaInAttesaPerFasciaOrariaEsistenteException("esiste già una visita in attesa o accettata per questo annuncio nella fascia oraria indicata.");
        }

        boolean esisteOffertaAccettataSuAnnuncio = offerteRepository.existsByAnnuncioAndStato(visite.getAnnuncio(), StatoOfferta.ACCETTATA);

        if(esisteOffertaAccettataSuAnnuncio) {
            throw new OffertaAccettataEsistenteException("esiste già un'offerta accettata per questo annuncio, quindi non è possibile prenotare visite");
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
