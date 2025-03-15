package com.househuntersbackend.demo.utils;

import com.househuntersbackend.demo.exceptions.VisitaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VisiteUtilsTest {

    @Mock
    private VisiteRepository visiteRepository;

    @Mock
    private OfferteRepository offerteRepository;

    private VisiteUtils visiteUtils;

    @BeforeEach
    void setUp() {
        this.visiteUtils = new VisiteUtils(visiteRepository, offerteRepository);
    }

    //1
    @Test
    void testDataValidaOrarioInizioMattinaOrarioFineMattina() {
        assertTrue(visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(11, 0)));
    }

    //2
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFinePomeriggio() {
        assertTrue(visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(14), LocalTime.of(14, 0), LocalTime.of(15, 0)));
    }

    //3
    @Test
    void testDataOggiOrarioInizioMattinaOrarioFineMattina() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0)));
    }

    //4
    @Test
    void testDataOltreDueSettimaneOrarioInizioMattinaOrarioFineMattina() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(15), LocalTime.of(11, 0), LocalTime.of(12, 0)));
    }

    //5
    @Test
    void testDataValidaOrarioInizioPrimaDiOrarioLavorativoOrarioFineMattina() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(10), LocalTime.of(6, 0), LocalTime.of(12, 0)));
    }

    //6
    @Test
    void testDataValidaOrarioInizioPausaPranzoOrarioFinePomeriggioOrarioFinePomeriggio() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(4), LocalTime.of(13, 0), LocalTime.of(15, 0)));
    }

    //7
    @Test
    void testDataValidaOrarioInizioDopoOrarioLavorativoOrarioFinePomeriggio() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(2), LocalTime.of(18, 0), LocalTime.of(18, 0)));
    }

    //8
    @Test
    void testDataValidaOrarioInizioMattinaOrarioFineInvalidoMattina() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(9, 0)));
    }

    //9
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFinePausaPranzo() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(4), LocalTime.of(11, 0), LocalTime.of(15, 0)));
    }

    //10
    @Test
    void testDataValidaOrarioInizioPomeriggioOrarioFineDopoOrarioLavorativo() {
        assertThrows(VisitaNonValidaException.class, () -> visiteUtils.areAttributiVisitaValidi(LocalDate.now().plusDays(7), LocalTime.of(16, 0), LocalTime.of(19, 0)));
    }
}
