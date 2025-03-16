package com.househuntersbackend.demo.verifiers;

import com.househuntersbackend.demo.entities.Offerte;
import com.househuntersbackend.demo.enumerations.StatoOfferta;
import com.househuntersbackend.demo.enumerations.UserType;
import com.househuntersbackend.demo.exceptions.OffertaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OffertaVerifier {
    OfferteRepository offerteRepository;

    public OffertaVerifier(OfferteRepository offerteRepository) {
        this.offerteRepository = offerteRepository;
    }

    public boolean areAttributiOffertaValidi(double valoreOfferta, double prezzoAnnuncio, LocalDate dataOfferta) throws OffertaNonValidaException {
        if(valoreOfferta <= 0) {
            throw new OffertaNonValidaException("Non si possono inserire offerte negative o nulle");
        }

        if(prezzoAnnuncio <= 0) {
            throw new OffertaNonValidaException("Un annuncio non può avere un prezzo negativo o nullo");
        }

        if(valoreOfferta >= prezzoAnnuncio) {
            throw new OffertaNonValidaException("L'offerta non può avere un valore maggiore del prezzo dell'annuncio");
        }

        if(!dataOfferta.isEqual(LocalDate.now())) {
            throw new OffertaNonValidaException("Data non valida");
        }

        return true;
    }

    public boolean isOffertaEsistente(Offerte offerte) throws OffertaNonValidaException {
        boolean esisteInAttesa = offerteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                offerte.getAnnuncio(), offerte.getCliente(), StatoOfferta.IN_ATTESA, StatoOfferta.CONTROPROPOSTA
        );

        if (esisteInAttesa && offerte.getCliente().getTipo().equals(UserType.CLIENTE)) {
            throw new OffertaNonValidaException("L'utente ha già un'offerta in attesa per questo annuncio.");
        }

        boolean esisteAccettata = offerteRepository.existsByAnnuncioAndStato(offerte.getAnnuncio(), StatoOfferta.ACCETTATA);

        if(esisteAccettata){
            throw new OffertaNonValidaException("Esiste già un'offerta accettata per l'annuncio");
        }

        return true;
    }
}
