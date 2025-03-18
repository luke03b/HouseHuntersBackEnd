package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.exceptions.VisitaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.utils.DateUtils;
import com.househuntersbackend.demo.verifiers.VisitaVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @BeforeEach
    void setUp() {
        this.visitaVerifier = new VisitaVerifier(visiteRepository, offerteRepository);
    }

    //1
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine sono validi")
    @CsvSource({
            "09:00, 10:00",
            "10:00, 11:00",
            "12:00, 13:00",
    })
    void testDataValidaOrarioInizioMattinaOrarioFineMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(2);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertTrue(visitaVerifier.areAttributiVisitaValidi(dataProva, orarioInizio, orarioFine));
    }


    //2
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine sono validi")
    @CsvSource({
            "14:00, 15:00",
            "15:00, 16:00",
            "16:00, 17:00",
            "17:00, 18:00",
    })
    void testDataValidaOrarioInizioPomeriggioOrarioFinePomeriggio(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(10);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertTrue(visitaVerifier.areAttributiVisitaValidi(dataProva, orarioInizio, orarioFine));
    }

    //3
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine sono validi, data oggi")
    @CsvSource({
            "09:00, 10:00",
            "10:00, 11:00",
            "12:00, 13:00",
    })
    void testDataOggiOrarioInizioMattinaOrarioFineMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now();
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, false);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //4
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine sono validi, data oltre due settimane")
    @CsvSource({
            "09:00, 10:00",
            "10:00, 11:00",
            "12:00, 13:00",
    })
    void testDataOltreDueSettimaneOrarioInizioMattinaOrarioFineMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(15);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //5
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio invalido e ' ' {1} ' ' orarioFine")
    @CsvSource({
            "08:00, 10:00",
            "07:00, 11:00",
            "04:00, 13:00",
    })
    void testDataValidaOrarioInizioPrimaDiOrarioLavorativoOrarioFineMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(10);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //6
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio invalido e ' ' {1} ' ' orarioFine")
    @CsvSource({
            "13:00, 15:00",
            "13:00, 16:00",
            "13:00, 17:00",
            "13:00, 18:00",
    })
    void testDataValidaOrarioInizioPausaPranzoOrarioFinePomeriggio(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(4);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //7
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio invalido e ' ' {1} ' ' orarioFine")
    @CsvSource({
            "18:00, 15:00",
            "19:00, 16:00",
            "20:00, 17:00",
            "21:00, 18:00",
    })
    void testDataValidaOrarioInizioDopoOrarioLavorativoOrarioFinePomeriggio(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(2);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //8
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine invalido")
    @CsvSource({
            "09:00, 09:00",
            "10:00, 08:00",
            "11:00, 07:00",
            "12:00, 06:00",
    })
    void testDataValidaOrarioInizioMattinaOrarioFineInvalidoMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(1);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //9
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine invalido")
    @CsvSource({
            "09:00, 14:00",
            "10:00, 14:00",
            "11:00, 14:00",
            "12:00, 14:00",
    })
    void testDataValidaOrarioInizioPomeriggioOrarioFinePausaPranzo(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(4);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //10
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine invalido")
    @CsvSource({
            "14:00, 19:00",
            "15:00, 20:00",
            "16:00, 21:00",
            "17:00, 22:00",
    })
    void testDataValidaOrarioInizioPomeriggioOrarioFineDopoOrarioLavorativo(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.now().plusDays(7);
        dataProva = DateUtils.assegnaDataNonFestiva(dataProva, true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //11
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine validi, data festività")
    @CsvSource({
            "09:00, 10:00",
            "10:00, 11:00",
            "12:00, 13:00",
    })
    void testDataFestivitaOrarioInizioMattinaOrarioFineMattina(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = LocalDate.of(2025, 4, 25);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }

    //12
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' orarioInizio e ' ' {1} ' ' orarioFine validi, data domenica")
    @CsvSource({
            "14:00, 15:00",
            "15:00, 16:00",
            "16:00, 17:00",
            "17:00, 18:00",
    })
    void testDataDomenicaOrarioInizioPomeriggioOrarioFinePomeriggio(LocalTime orarioInizio, LocalTime orarioFine) {
        LocalDate dataProva = DateUtils.assegnaDomenica(LocalDate.now(), true);

        assertVisitaNonValida(dataProva, orarioInizio, orarioFine);
    }
}