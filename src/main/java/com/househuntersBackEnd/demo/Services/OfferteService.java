package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Enumerations.StatoAnnuncio;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import com.househuntersBackEnd.demo.Enumerations.UserType;
import com.househuntersBackEnd.demo.Exceptions.OffertaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferteService {
    @Autowired
    private OfferteRepository offerteRepository;
    @Autowired
    private AnnuncioService annuncioService;


    public Offerte createOfferte(Offerte offerte) throws OffertaInAttesaEsistenteException {
        if(offerte.getCliente() != null) {
            boolean esisteInAttesa = offerteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                    offerte.getAnnuncio(), offerte.getCliente(), StatoOfferta.IN_ATTESA, StatoOfferta.CONTROPROPOSTA
            );
            if (esisteInAttesa && offerte.getCliente().getTipo().equals(UserType.CLIENTE)) {
                throw new OffertaInAttesaEsistenteException("L'utente ha già un'offerta in attesa per questo annuncio.");
            }
        }
        offerte.setStato(StatoOfferta.IN_ATTESA);
        offerte.setData(LocalDateTime.now());
        return offerteRepository.save(offerte);
    }

    public List<Offerte> getAllOfferteOnAnnuncio(UUID idAnnuncio) {
        return offerteRepository.findOfferteByAnnuncioId(idAnnuncio);
    }

    public List<Offerte> getOfferteConAnnuncioByClienteId(UUID idCliente) {
        return offerteRepository.findOfferteConAnnuncioByClienteId(idCliente);
    }

    @Transactional
    public Offerte updateStatoOfferte(Offerte offerte, String stato) {
        Optional<Offerte> existingOfferta = offerteRepository.findById(offerte.getId());

        if (existingOfferta.isPresent()) {
            Offerte offertaToUpdate = existingOfferta.get();

            if(stato.equals(StatoOfferta.RIFIUTATA.toString())) {
                offertaToUpdate.setStato(StatoOfferta.RIFIUTATA);
            } else if (stato.equals(StatoOfferta.IN_ATTESA.toString())){
                offertaToUpdate.setStato(StatoOfferta.IN_ATTESA);
            } else {
                offertaToUpdate.setStato(StatoOfferta.ACCETTATA);
                offerteRepository.updateStatoOfferteEscluse(offertaToUpdate.getAnnuncio().getId(), offertaToUpdate.getId(), StatoOfferta.RIFIUTATA);
                annuncioService.updateStatoAnnuncio(offerte.getAnnuncio().getId(), StatoAnnuncio.CONCLUSO);
            }

            // Salva e restituisci l'entità aggiornata
            return offerteRepository.save(offertaToUpdate);
        } else {
            throw new EntityNotFoundException("Offerta non trovata con ID: " + offerte.getId());
        }
    }

    public Offerte updateStatoOfferteControproposta(Offerte offerte, Optional<Double> controproposta) {
        Optional<Offerte> existingOfferta = offerteRepository.findById(offerte.getId());

        if (existingOfferta.isPresent()) {
            Offerte offertaToUpdate = existingOfferta.get();

            offertaToUpdate.setStato(StatoOfferta.CONTROPROPOSTA);
            offertaToUpdate.setControProposta(controproposta.get());

            return offerteRepository.save(offertaToUpdate);
        } else {
            throw new EntityNotFoundException("Offerta non trovata con ID: " + offerte.getId());
        }
    }

    public List<Offerte> getOfferteSuAnnuncioByStato(UUID idAnnuncio, String stato) {
        StatoOfferta statoFormattato;
        if(stato.equals(StatoOfferta.RIFIUTATA.toString())) {
            statoFormattato = StatoOfferta.RIFIUTATA;
        } else if (stato.equals(StatoOfferta.IN_ATTESA.toString())){
            statoFormattato = StatoOfferta.IN_ATTESA;
        } else if (stato.equals(StatoOfferta.ACCETTATA.toString())){
            statoFormattato = StatoOfferta.ACCETTATA;
        } else {
            statoFormattato = StatoOfferta.CONTROPROPOSTA;
        }

        // Salva e restituisci l'entità aggiornata
        return offerteRepository.findOfferteByAnnuncioIdAndStato(idAnnuncio, statoFormattato);
    }
}
