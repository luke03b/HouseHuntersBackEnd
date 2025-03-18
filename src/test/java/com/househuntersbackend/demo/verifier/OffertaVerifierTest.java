package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.exceptions.OffertaNonValidaException;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.verifiers.OffertaVerifier;
import org.junit.jupiter.api.BeforeEach;
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
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' valoreOfferta e ' ' {1} ' ' prezzoAnnuncio sono validi")
    @CsvSource({
            "1, 1",
            "2, 2",
            "200000, 500000",
            "1000000, 2000000",
            "90000000, 100000000",
    })
    void testValoreOffertaValidoPrezzoAnnuncioValidoDataValida(double valoreofferta, double prezzoAnnuncio) {
        LocalDate dataOfferta = LocalDate.now();

        boolean res = offertaVerifier.areAttributiOffertaValidi(valoreofferta, prezzoAnnuncio, dataOfferta);

        assertTrue(res);
    }

    //2
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' valoreOfferta e ' ' {1} ' ' prezzoAnnuncio invalido")
    @CsvSource({
            "1, 0",
            "2, -1",
            "200000, -200000",
            "1000000, -2000000",
            "90000000, -100000000",
    })
    void testValoreOffertaValidoPrezzoAnnuncioNonValidoDataValida(double valoreOfferta, double prezzoAnnuncio) {
        LocalDate dataOfferta = LocalDate.now();

        assertOffertaNonValida(valoreOfferta, prezzoAnnuncio, dataOfferta);
    }

    //3
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' valoreOfferta e ' ' {1} ' ' prezzoAnnuncio sono validi, data invalida")
    @CsvSource({
            "1, 1",
            "2, 2",
            "200000, 500000",
            "1000000, 2000000",
            "90000000, 100000000",
    })
    void testValoreOffertaValidoPrezzoAnnuncioValidoDataInvalida(double valoreOfferta, double prezzoAnnuncio) {
        LocalDate dataOfferta = LocalDate.now().plusDays(2);

        assertOffertaNonValida(valoreOfferta, prezzoAnnuncio, dataOfferta);
    }

    //4
    @ParameterizedTest(name = "Test {index} ==> ' ' {0} ' ' valoreOfferta invalido e ' ' {1} ' ' prezzoAnnuncio")
    @CsvSource({
            "0, 1",
            "-1, 2",
            "-200000, 500000",
            "-1000000, 2000000",
            "-90000000, 100000000",
    })
    void testValoreOffertaInvalidoPrezzoAnnuncioValidoDataInvalida(double valoreOfferta, double prezzoAnnuncio) {
        LocalDate dataOfferta = LocalDate.now();

        assertOffertaNonValida(valoreOfferta, prezzoAnnuncio, dataOfferta);
    }
}
