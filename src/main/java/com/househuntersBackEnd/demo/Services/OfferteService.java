package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import com.househuntersBackEnd.demo.Exceptions.OffertaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OfferteService {
    @Autowired
    private OfferteRepository offerteRepository;
    public Offerte createOfferte(Offerte offerte) throws OffertaInAttesaEsistenteException {
        boolean esisteInAttesa = offerteRepository.existsByAnnuncioAndClienteAndStato(
                offerte.getAnnuncio(), offerte.getCliente(), StatoOfferta.IN_ATTESA
        );
        if (esisteInAttesa) {
            throw new OffertaInAttesaEsistenteException("L'utente ha già un'offerta in attesa per questo annuncio.");
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
}
