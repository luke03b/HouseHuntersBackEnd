package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.verifiers.AnnuncioVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AnnuncioVerifierTest {

    private AnnuncioVerifier annuncioVerifier;

    @BeforeEach
    void setUp() {
        this.annuncioVerifier = new AnnuncioVerifier();
    }

    //1
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' superficie e ' ' {1} ' ' stanze sono validi")
    @CsvSource({
            "1, 1",
            "2, 2",
            "500000, 1000",
    })
    void testSuperficieValidaNumeroStanzeValido(int superficie, int numeroStanze) {
        boolean res = annuncioVerifier.areSuperficieENumeroStanzeValidi(superficie, numeroStanze);

        assertTrue(res);
    }

    //2
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' superficie è invalida ma ' ' {1} ' ' stanze è valido")
    @CsvSource({
            "0, 1",
            "-1, 2",
            "-500000, 1000",
    })
    void testSuperficieInvalidaNumeroStanzeValido(int superficie, int numeroStanze) {
        boolean res = annuncioVerifier.areSuperficieENumeroStanzeValidi(superficie, numeroStanze);

        assertFalse(res);
    }

    //3
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' superficie è valida ma ' ' {1} ' ' stanze è invalido")
    @CsvSource({
            "1, 0",
            "2, -1",
            "500000, -1000",
    })
    void testSuperficieValidaNumeroStanzeInvalido(int superficie, int numeroStanze) {
        boolean res = annuncioVerifier.areSuperficieENumeroStanzeValidi(superficie, numeroStanze);

        assertFalse(res);
    }

    //1
    @Test
    void testPianoTERRANumeroPianoNULL() {
        Piano piano = Piano.TERRA;
        Integer numeroPiano = null;

        boolean res = annuncioVerifier.arePianoENumeroPianoValidi(piano, numeroPiano);

        assertTrue(res);
    }

    //2
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' numeroPiano valido")
    @CsvSource({
            "1",
            "2",
            "500000",
    })
    void testPianoINTERMEDIONumeroPianoPositivo(Integer numeroPiano) {
        assertTrue(annuncioVerifier.arePianoENumeroPianoValidi(Piano.INTERMEDIO, numeroPiano));
    }

    //3
    @Test
    void testPianoULTIMONumeroPianoNULL() {
        Piano piano = Piano.ULTIMO;
        Integer numeroPiano = null;

        boolean res = annuncioVerifier.arePianoENumeroPianoValidi(piano, numeroPiano);

        assertTrue(res);
    }

    //4
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' numeroPiano invalido")
    @CsvSource({
            "0",
            "-1",
            "-500000",
    })
    void testPianoINTERMEDIONumeroPianoInvalido(Integer numeroPiano) {
        Piano piano = Piano.INTERMEDIO;

        boolean res = annuncioVerifier.arePianoENumeroPianoValidi(piano, numeroPiano);

        assertFalse(res);
    }

    //1
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine ' ' {2} ' ' longitudine sono validi")
    @CsvSource({
            "\"Antartide\", -90, -180",
            "\"Antartide 2\", -89, -179",
            "\"Oceano Atlantico Meridionale\", 0, 0",
            "\"Mar Glaciale Artico 2\", 89, 179",
            "\"Mar Glaciale Artico\", 90, 180"
    })
    void testIndirizzoValidoLatitudineValidaLongitudineValida(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertTrue(res);
    }

    //2
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine invalida ' ' {2} ' ' longitudine")
    @CsvSource({
            "\"Posto sconosciuto\", -92, -180",
            "\"Posto meno sconosciuto\", -91, -179",
            "\"posto disperso\", -200, 0",
            "\"in un universo parallelo\", -4000, 179",
            "\"ancora più disperso\", -6000, 180"
    })
    void testIndirizzoValidoLatitudineMinoreLongitudineValida(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }

    //3
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine ' ' {2} ' ' longitudine invalida")
    @CsvSource({
            "\"Posto sconosciuto2\", -90, -182",
            "\"Posto meno sconosciuto2\", -89, -181",
            "\"posto disperso2\", -0, -300",
            "\"in un universo parallelo2\", 89, -3000",
            "\"ancora più disperso2\", 90, -6000"
    })
    void testIndirizzoValidoLatitudineValidaLongitudineMinore(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }

    //4
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine ' ' {2} ' ' longitudine")
    @CsvSource({
            ", -90, -180",
            ", -89, -179",
            ", -0, 0",
            ", 89, 179",
            ", 90, 180"
    })
    void testIndirizzoNULLlatitudineValidaLongitudineValida(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }

    //5
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine invalida ' ' {2} ' ' longitudine")
    @CsvSource({
            "\"Posto sconosciuto3\", 92, -180",
            "\"Posto meno sconosciuto3\", 91, -179",
            "\"posto disperso3\", 200, 0",
            "\"in un universo parallelo3\", 4000, 179",
            "\"ancora più disperso3\", 6000, 180"
    })
    void testIndirizzoValidoLatitudineMaggioreLongitudineValida(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }

    //6
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo, ' ' {1} ' ' latitudine ' ' {2} ' ' longitudine invalida")
    @CsvSource({
            "\"Posto sconosciuto2\", -90, 182",
            "\"Posto meno sconosciuto2\", -89, 181",
            "\"posto disperso2\", -0, 300",
            "\"in un universo parallelo2\", 89, 3000",
            "\"ancora più disperso2\", 90, 6000"
    })
    void testIndirizzoValidoLatitudineValidaLongitudineMaggiore(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }

    //7
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' indirizzo vuoto, ' ' {1} ' ' latitudine ' ' {2} ' ' longitudine")
    @CsvSource({
            "'', -90, -180",
            "'', -89, -179",
            "'', -0, 0",
            "'', 89, 179",
            "'', 90, 180"
    })
    void testIndirizzoVuotoLatitudineValidaLongitudineValida(String indirizzo, double latitudine, double longitudine) {
        boolean res = annuncioVerifier.isPosizioneAnnuncioValida(indirizzo, latitudine, longitudine);

        assertFalse(res);
    }
}