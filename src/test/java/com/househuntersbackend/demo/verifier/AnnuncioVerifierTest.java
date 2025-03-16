package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.verifiers.AnnuncioVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnuncioVerifierTest {

    private AnnuncioVerifier annuncioVerifier;

    @BeforeEach
    void setUp() {
        this.annuncioVerifier = new AnnuncioVerifier();
    }

    //1
    @Test
    void testSuperficieValidaNumeroStanzeValido() {
        assertTrue(annuncioVerifier.areSuperficieENumeroStanzeValidi(100, 5));
    }

    //2
    @Test
    void testSuperficieInvalidaNumeroStanzeValido() {
        assertFalse(annuncioVerifier.areSuperficieENumeroStanzeValidi(-100, 7));
    }

    //3
    @Test
    void testSuperficieValidaNumeroStanzeInvalido() {
        assertFalse(annuncioVerifier.areSuperficieENumeroStanzeValidi(80, -3));
    }

    //1
    @Test
    void testPianoTERRANumeroPianoNULL() {
        assertTrue(annuncioVerifier.arePianoENumeroPianoValidi(Piano.TERRA, null));
    }

    //2
    @Test
    void testPianoINTERMEDIONumeroPianoPositivo() {
        assertTrue(annuncioVerifier.arePianoENumeroPianoValidi(Piano.INTERMEDIO, 1));
    }

    //3
    @Test
    void testPianoULTIMONumeroPianoNULL() {
        assertTrue(annuncioVerifier.arePianoENumeroPianoValidi(Piano.ULTIMO, null));
    }

    //4
    @Test
    void testPianoINTERMEDIONumeroPianoNegativo() {
        assertFalse(annuncioVerifier.arePianoENumeroPianoValidi(Piano.INTERMEDIO, -1));
    }

    //1
    @Test
    void testIndirizzoValidoLatitudineValidaLongitudineValida() {
        assertTrue(annuncioVerifier.isPosizioneAnnuncioValida("Minecraft city, Chemin Rémy, Dampierre-en-Burly, France", 47.73909253263101, 2.5195524489149523));
    }

    //2
    @Test
    void testIndirizzoValidoLatitudineMinoreLongitudineValida() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida("Strada Provinciale 330, 8, Raviscanina, Province of Caserta, Italy", -91.24380, 100.3489230));
    }

    //3
    @Test
    void testIndirizzoValidoLatitudineValidaLongitudineMinore() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida("Via Isonzo, 8, Alvignano, Province of Caserta, Italy", 7.3294238, -182.348093));
    }

    //4
    @Test
    void testIndirizzoNULLlatitudineValidaLongitudineValida() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida(null, 10.1238291309, 45.342908));
    }

    //5
    @Test
    void testIndirizzoValidoLatitudineMaggioreLongitudineValida() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida("Via Floriano del Secolo, 3, Napoli, Metropolitan City of Naples, Italy", 92.210398, 80.34098));
    }

    //6
    @Test
    void testIndirizzoValidoLatitudineValidaLongitudineMaggiore() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida("Via Dalmazia, 13, Napoli, Metropolitan City of Naples, Italy", 50.023498, 182.234098));
    }

    //7
    @Test
    void testIndirizzoVuotoLatitudineValidaLongitudineValida() {
        assertFalse(annuncioVerifier.isPosizioneAnnuncioValida("", 10.1238291309, 45.342908));
    }
}