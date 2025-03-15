package com.househuntersbackend.demo.utils;

import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.enumerations.StatoOfferta;
import com.househuntersbackend.demo.enumerations.StatoVisita;
import com.househuntersbackend.demo.exceptions.VisitaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class VisiteUtils {

    private final VisiteRepository visiteRepository;
    private final OfferteRepository offerteRepository;
    private static final LocalDate GIORNO_MINIMO_PRENOTAZIONE = LocalDate.now().plusDays(1);
    private static final LocalDate GIORNO_MASSIMO_PRENOTAZIONE = GIORNO_MINIMO_PRENOTAZIONE.plusDays(13);
    private static final LocalTime ORARIO_INIZIO_MINIMO_PRENOTAZIONE = LocalTime.of(9, 0);
    private static final LocalTime ORARIO_INIZIO_MASSIMO_PRENOTAZIONE = LocalTime.of(17, 0);
    private static final LocalTime ORARIO_FINE_MINIMO_PRENOTAZIONE = LocalTime.of(10, 0);
    private static final LocalTime ORARIO_FINE_MASSIMO_PRENOTAZIONE = LocalTime.of(18, 0);
    private static final LocalTime ORARIO_INIZIO_PAUSA_PRANZO = LocalTime.of(13, 0);
    private static final LocalTime ORARIO_FINE_PAUSA_PRANZO = LocalTime.of(14, 0);

    public VisiteUtils(VisiteRepository visiteRepository, OfferteRepository offerteRepository) {
        this.visiteRepository = visiteRepository;
        this.offerteRepository = offerteRepository;
    }

    public boolean areAttributiVisitaValidi(LocalDate dataVisita, LocalTime orarioInizioVisita, LocalTime orarioFineVisita) throws VisitaNonValidaException {

        if (DateUtils.getFestivitaItaliane().contains(dataVisita)) {
            throw new VisitaNonValidaException("Non e' possibile prenotare visite nei giorni festivi.");
        }

        if (dataVisita.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new VisitaNonValidaException("Non e' possibile prenotare visite di domenica.");
        }

        if(dataVisita.isBefore(GIORNO_MINIMO_PRENOTAZIONE) || dataVisita.isAfter(GIORNO_MASSIMO_PRENOTAZIONE)) {
            throw new VisitaNonValidaException("Data non valida, e' possibile prenotare visite nei giorni dal " + GIORNO_MINIMO_PRENOTAZIONE + " al " + GIORNO_MASSIMO_PRENOTAZIONE);
        }

        if(orarioInizioVisita.isAfter(orarioFineVisita)) {
            throw new VisitaNonValidaException("L'orario di inizio visita deve essere maggiore dell'orario di fine visita, a meno che tu non sappia viaggiare nel tempo");
        }

        if(orarioInizioVisita.isBefore(ORARIO_INIZIO_MINIMO_PRENOTAZIONE) || orarioInizioVisita.isAfter(ORARIO_INIZIO_MASSIMO_PRENOTAZIONE)) {
            throw new VisitaNonValidaException("Orario inizio visita non valido, le visite possono avere come orario d'inizio un valore che vada dalle "
                    + ORARIO_INIZIO_MINIMO_PRENOTAZIONE + " alle " + ORARIO_INIZIO_PAUSA_PRANZO.minusHours(1) + " e dalle " + ORARIO_FINE_PAUSA_PRANZO +
                    " alle " + ORARIO_INIZIO_MASSIMO_PRENOTAZIONE);
        }

        if(orarioFineVisita.isBefore(ORARIO_FINE_MINIMO_PRENOTAZIONE) || orarioFineVisita.isAfter(ORARIO_FINE_MASSIMO_PRENOTAZIONE)) {
            throw new VisitaNonValidaException("Orario fine visita non valido, le visite possono avere come orario di fine un valore che vada dalle "
                    + ORARIO_FINE_MINIMO_PRENOTAZIONE + " alle " + ORARIO_INIZIO_PAUSA_PRANZO + " e dalle " + ORARIO_FINE_PAUSA_PRANZO.plusHours(1) +
                    " alle " + ORARIO_FINE_MASSIMO_PRENOTAZIONE);
        }

        if ((orarioInizioVisita.isAfter(ORARIO_INIZIO_PAUSA_PRANZO) && orarioInizioVisita.isBefore(ORARIO_FINE_PAUSA_PRANZO)) ||
                (orarioFineVisita.isAfter(ORARIO_INIZIO_PAUSA_PRANZO) && orarioFineVisita.isBefore(ORARIO_FINE_PAUSA_PRANZO)) ||
                orarioInizioVisita.equals(ORARIO_INIZIO_PAUSA_PRANZO) || orarioFineVisita.equals(ORARIO_FINE_PAUSA_PRANZO) ||
                (orarioInizioVisita.isBefore(ORARIO_INIZIO_PAUSA_PRANZO) && orarioFineVisita.isAfter(ORARIO_FINE_PAUSA_PRANZO))) {
            throw new VisitaNonValidaException("Orario non valido, non e' possibile prenotare una visita durante la pausa pranzo degli agenti, ovvero dalle "
                    + ORARIO_INIZIO_PAUSA_PRANZO + " alle " + ORARIO_FINE_PAUSA_PRANZO);
        }

        return true;

    }

    public boolean isVisitaEsistente(Visite visita) throws VisitaNonValidaException {
        boolean esisteInAttesaOAccettataCliente = visiteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                visita.getCliente(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visita.getAnnuncio()
        );

        if (esisteInAttesaOAccettataCliente) {
            throw new VisitaNonValidaException("L'utente ha gia' una visita in attesa o confermata per questo annuncio.");
        }

        boolean esisteInAttesaOAccettataFasciaOraria = visiteRepository.existsByAnnuncioAndStatoOrStatoAndDataAndOrarioInizio(
                visita.getAnnuncio(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA, visita.getData(), visita.getOrarioInizio()
        );

        if(esisteInAttesaOAccettataFasciaOraria) {
            throw new VisitaNonValidaException("esiste gia' una visita in attesa o accettata per questo annuncio nella fascia oraria indicata.");
        }

        boolean esisteOffertaAccettataSuAnnuncio = offerteRepository.existsByAnnuncioAndStato(visita.getAnnuncio(), StatoOfferta.ACCETTATA);

        if(esisteOffertaAccettataSuAnnuncio) {
            throw new VisitaNonValidaException("esiste gia' un'offerta accettata per questo annuncio, quindi non e' possibile prenotare visite");
        }

        return true;
    }
}
