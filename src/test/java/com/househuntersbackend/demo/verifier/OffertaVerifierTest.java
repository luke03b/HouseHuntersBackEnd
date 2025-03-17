package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.exceptions.OffertaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.verifiers.OffertaVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OffertaVerifierTest {
    @Mock
    private OfferteRepository offerteRepository;

    private OffertaVerifier offertaVerifier;

    private void assertOffertaNonValida(double valoreOfferta, double prezzoAnnuncio, LocalDate dataOfferta) {
        assertThrows(OffertaNonValidaException.class, () -> offertaVerifier.areAttributiOffertaValidi(valoreOfferta, prezzoAnnuncio, dataOfferta));
    }

    @BeforeEach
    void setUp() {
        this.offertaVerifier = new OffertaVerifier(offerteRepository);
    }

    //1
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' valoreOfferta e ' ' {1} ' ' prezzoAnnuncio  ' ' {2} ' ' dataOfferta sono validi")
    @CsvSource({
            "1, 1",
            "2, 2",
            "500000, 1000",
    })
    void testValoreOffertaValidoPrezzoAnnuncioValidoDataValida(double valoreofferta, double prezzoAnnuncio, LocalDate dataOfferta) {
        assertTrue(offertaVerifier.areAttributiOffertaValidi(100000, 120000, LocalDate.now()));
    }

    //2
    @Test
    void testValoreOffertaValidoPrezzoAnnuncioNonValidoDataValida() {
        assertOffertaNonValida(50000, 0, LocalDate.now());
    }

    //3
    @Test
    void testValoreOffertaValidoPrezzoAnnuncioValidoDataInvalida() {
        assertOffertaNonValida(50000, 100000, LocalDate.now().plusDays(2));
    }

    //4
    @Test
    void testValoreOffertaInvalidoPrezzoAnnuncioValidoDataInvalida() {
        assertOffertaNonValida(110000, 100000, LocalDate.now());
    }
}
