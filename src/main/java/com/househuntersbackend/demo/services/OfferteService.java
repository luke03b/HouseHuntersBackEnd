package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Offerte;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.enumerations.StatoOfferta;
import com.househuntersbackend.demo.enumerations.StatoVisita;
import com.househuntersbackend.demo.exceptions.OffertaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.utils.OffertaUtils;
import com.househuntersbackend.demo.verifiers.OffertaVerifier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferteService {
    private final OffertaVerifier offertaVerifier;
    private final OfferteRepository offerteRepository;
    private final AnnuncioService annuncioService;
    private final VisiteRepository visiteRepository;

    public OfferteService(OfferteRepository offerteRepository, AnnuncioService annuncioService, VisiteRepository visiteRepository, OffertaVerifier offertaVerifier) {
        this.offerteRepository = offerteRepository;
        this.annuncioService = annuncioService;
        this.visiteRepository = visiteRepository;
        this.offertaVerifier = offertaVerifier;
    }

    public Offerte createOfferte(Offerte offerte) throws OffertaNonValidaException {
        try{
            offertaVerifier.isOffertaEsistente(offerte);
            offerte.setStato(StatoOfferta.IN_ATTESA);
            offerte.setData(LocalDateTime.now());
            offertaVerifier.areAttributiOffertaValidi(offerte.getPrezzo(), offerte.getAnnuncio().getPrezzo(), offerte.getData().toLocalDate());
            return offerteRepository.save(offerte);
        } catch (OffertaNonValidaException e) {
            throw new OffertaNonValidaException(e.getMessage());
        }
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

            offertaToUpdate.setStato(OffertaUtils.mappaStatoOfferta(stato));
            if(offertaToUpdate.getStato().equals(StatoOfferta.ACCETTATA)) {
                offerteRepository.updateStatoOfferteEscluse(offertaToUpdate.getAnnuncio().getId(), offertaToUpdate.getId(), StatoOfferta.RIFIUTATA);
                if(!OffertaUtils.isOffertaManuale(offertaToUpdate)) {
                    visiteRepository.updateStatoVisiteEscluse(offertaToUpdate.getAnnuncio().getId(), offertaToUpdate.getCliente().getId(), StatoVisita.RIFIUTATA);
                } else {
                    visiteRepository.updateStatoVisitePerAnnuncio(offertaToUpdate.getAnnuncio().getId(), StatoVisita.RIFIUTATA);
                }
                annuncioService.updateStatoAnnuncio(offerte.getAnnuncio().getId(), StatoAnnuncio.CONCLUSO);
            }

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
            controproposta.ifPresent(offertaToUpdate::setControProposta);

            return offerteRepository.save(offertaToUpdate);
        } else {
            throw new EntityNotFoundException("Offerta non trovata con ID: " + offerte.getId());
        }
    }

    public List<Offerte> getOfferteSuAnnuncioByStato(UUID idAnnuncio, String stato) {
        return offerteRepository.findOfferteByAnnuncioIdAndStato(idAnnuncio, OffertaUtils.mappaStatoOfferta(stato));
    }
}
