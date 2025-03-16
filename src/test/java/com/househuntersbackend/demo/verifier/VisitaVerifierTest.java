package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.exceptions.VisitaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.utils.DateUtils;
import com.househuntersbackend.demo.verifiers.VisitaVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VisitaVerifierTest {

    @Mock
    private VisiteRepository visiteRepository;

    @Mock
    private OfferteRepository offerteRepository;

    private VisitaVerifier visitaVerifier;

    private void assertVisitaNonValida(LocalDate data, LocalTime inizio, LocalTime fine) {
        assertThrows(VisitaNonValidaException.class, () -> visitaVerifier.areAttributiVisitaValidi(data, inizio, fine));
    }

    private LocalDate assegnaDataNonFestiva(LocalDate dataProva, boolean aggiungiGiorni) {
        if(dataProva.getDayOfWeek() == DayOfWeek.SUNDAY || DateUtils.getFestivitaItaliane().contains(dataProva)) {
            if(aggiungiGiorni) {
                dataProva = dataProva.plusDays(1);
            } else {
                dataProva = dataProva.minusDays(1);
            }
            dataProva = assegnaDataNonFestiva(dataProva, aggiungiGiorni);
        }
        return dataProva;
    }

    private LocalDate assegnaDomenica(LocalDate dataProva, boolean aggiungiGiorni) {
        if(dataProva.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if(aggiungiGiorni) {
                dataProva = dataProva.plusDays(1);
            } else {
                dataProva = dataProva.minusDays(1);
            }
            dataProva = assegnaDomenica(dataProva, aggiungiGiorni);
        }
        return dataProva;
    }

    @BeforeEach
    void setUp() {
        this.visitaVerifier = new VisitaVerifier(visiteRepository, offerteRepository);
    }

    //1
    @Test
    void testDataValidaOrarioInizioMattinaOrarioFineMattina() {
        LocalDate dataProva = LocalDate.now().plusDays(2);
        dataProva = assegnaDataNonFestiva(dataProva, true);

        assertTrue(visitaVerifier.areAttributiVisitaValidi(dataProva, LocalTime.of(10, 0), LocalTime.of(11, 0)));
    }


    //2
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFinePomeriggio() {
        LocalDate dataProva = LocalDate.now().plusDays(10);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertTrue(visitaVerifier.areAttributiVisitaValidi(dataProva, LocalTime.of(14, 0), LocalTime.of(15, 0)));
    }

    //3
    @Test
    void testDataOggiOrarioInizioMattinaOrarioFineMattina() {
        LocalDate dataProva = LocalDate.now();
        dataProva = assegnaDataNonFestiva(dataProva, false);
        assertVisitaNonValida(dataProva, LocalTime.of(9, 0), LocalTime.of(10, 0));
    }

    //4
    @Test
    void testDataOltreDueSettimaneOrarioInizioMattinaOrarioFineMattina() {
        LocalDate dataProva = LocalDate.now().plusDays(15);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(11, 0), LocalTime.of(12, 0));
    }

    //5
    @Test
    void testDataValidaOrarioInizioPrimaDiOrarioLavorativoOrarioFineMattina() {
        LocalDate dataProva = LocalDate.now().plusDays(10);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(6, 0), LocalTime.of(12, 0));
    }

    //6
    @Test
    void testDataValidaOrarioInizioPausaPranzoOrarioFinePomeriggio() {
        LocalDate dataProva = LocalDate.now().plusDays(4);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(13, 0), LocalTime.of(15, 0));
    }

    //7
    @Test
    void testDataValidaOrarioInizioDopoOrarioLavorativoOrarioFinePomeriggio() {
        LocalDate dataProva = LocalDate.now().plusDays(2);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(18, 0), LocalTime.of(18, 0));
    }

    //8
    @Test
    void testDataValidaOrarioInizioMattinaOrarioFineInvalidoMattina() {
        LocalDate dataProva = LocalDate.now().plusDays(1);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(9, 0), LocalTime.of(9, 0));
    }

    //9
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFinePausaPranzo() {
        LocalDate dataProva = LocalDate.now().plusDays(4);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(11, 0), LocalTime.of(15, 0));
    }

    //10
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFineDopoOrarioLavorativo() {
        LocalDate dataProva = LocalDate.now().plusDays(7);
        dataProva = assegnaDataNonFestiva(dataProva, true);
        assertVisitaNonValida(dataProva, LocalTime.of(16, 0), LocalTime.of(19, 0));
    }

    //11
    @Test
    void testDataFestivitaOrarioInizioMattinaOrarioFineMattina() {
        assertVisitaNonValida(LocalDate.of(2025, 4, 25), LocalTime.of(9, 0), LocalTime.of(10, 0));
    }

    //12
    @Test
    void testDataDomenicaOrarioInizioPomeriggioOrarioFinePomeriggio() {
        LocalDate dataProva = assegnaDomenica(LocalDate.now(), true);
        assertVisitaNonValida(dataProva, LocalTime.of(15, 0), LocalTime.of(18, 0));
    }
}
