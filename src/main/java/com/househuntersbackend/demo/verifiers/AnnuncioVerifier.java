package com.househuntersbackend.demo.verifiers;

import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.exceptions.AnnuncioNonValidoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AnnuncioVerifier {
    public boolean areAttributiAnnuncioValidi(Annunci annuncio) throws AnnuncioNonValidoException {
        if(!areSuperficieENumeroStanzeValidi(annuncio.getSuperficie(), annuncio.getNumStanze())) {
            throw new AnnuncioNonValidoException("I dettagli strutturali dell'immobile sono errati, superficie e numero stanze devono essere maggiori di 0");
        }

        if(!arePianoENumeroPianoValidi(annuncio.getPiano(), annuncio.getNumeroPiano())) {
            throw new AnnuncioNonValidoException("Il numero del piano va inserito solamente se il piano e' intermedio, In questo caso il numero piano deve essere maggiore di 0");
        }

        if(annuncio.getPrezzo() <= 0) {
            throw new AnnuncioNonValidoException("Il prezzo dell'annuncio deve essere maggiore di 0");
        }

        if(!annuncio.getDataCreazione().toLocalDate().equals(LocalDate.now())) {
            throw new AnnuncioNonValidoException("La data creazione deve essere quella odierna");
        }

        if(annuncio.getDescrizione().isEmpty() || annuncio.getDescrizione().isBlank()) {
            throw new AnnuncioNonValidoException("Inserire una descrizione");
        }

        if(!isPosizioneAnnuncioValida(annuncio.getIndirizzo(), annuncio.getLatitudine(), annuncio.getLongitudine())) {
            throw new AnnuncioNonValidoException("La posizione (indirizzo, latitudine, longitudine) dell'annuncio non è valida");
        }

        return true;
    }

    public boolean areSuperficieENumeroStanzeValidi(int superficie, int numeroStanze) {
        return superficie > 0 && numeroStanze > 0;
    }

    public boolean arePianoENumeroPianoValidi(Piano piano, Integer numeroPiano) {
        if(piano.equals(Piano.INTERMEDIO) && numeroPiano > 0) {
            return true;
        }

        return piano.equals(Piano.ULTIMO) || piano.equals(Piano.TERRA) && numeroPiano == null;
    }

    public boolean isPosizioneAnnuncioValida(String indirizzo, double latitudine, double longitudine) {
        if (!isIndirizzoValido(indirizzo)) return false;

        if(latitudine < -90 || latitudine > 90) return false;

        return longitudine >= -180 && longitudine <= 180;
    }

    public boolean isIndirizzoValido(String indirizzo) {
        if(indirizzo == null) {
            return false;
        }

        if(indirizzo.isEmpty()) {
            return false;
        }

        return indirizzo.matches("^[A-Za-z0-9 ,.'-]+$");
    }
}