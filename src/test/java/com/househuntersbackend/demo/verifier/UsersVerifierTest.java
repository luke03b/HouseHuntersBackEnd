package com.househuntersbackend.demo.verifier;

import com.househuntersbackend.demo.enumerations.TipoUtente;
import com.househuntersbackend.demo.exceptions.UtenteNonValidoException;
import com.househuntersbackend.demo.verifiers.UsersVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersVerifierTest {
    private UsersVerifier usersVerifier;

    private void assertUtenteNonValido(String email, String nome, String cognome, TipoUtente tipo, String idAgenziaImmobiliare) {
        assertThrows(UtenteNonValidoException.class, () -> usersVerifier.areUserAttributiValidi(email, nome, cognome, tipo, idAgenziaImmobiliare));
    }

    @BeforeEach
    void setUp() {
        usersVerifier = new UsersVerifier();
    }

    //1
    @Test
    void testClienteValido() {
        assertTrue(usersVerifier.areUserAttributiValidi("lukebuono74@gmail.com", "Luca", "Buonomo",
                TipoUtente.CLIENTE, null));
    }

    //2
    @Test
    void testAgenteValido() {
        assertTrue(usersVerifier.areUserAttributiValidi("lucabuonomo@hotmail.com", "luca", "buonomo",
                TipoUtente.AGENTE, "ec56d024-7520-4829-a760-faa14fc50a8c"));
    }

    //3
    @Test
    void testAdminValido() {
        assertTrue(usersVerifier.areUserAttributiValidi("alecento8@icloud.com", "Alessio", "Centonze",
                TipoUtente.ADMIN, "ec56d024-7520-4829-a760-faa14fc50a8c"));
    }

    //4
    @Test
    void testEmailNull() {
        assertUtenteNonValido(null, "antonio", "dex",
                TipoUtente.CLIENTE, null);
    }

    //5
    @Test
    void testEmailVuota() {
        assertUtenteNonValido("", "Luchino", "buonomino",
                TipoUtente.AGENTE, "ec56d024-7520-4829-a760-faa14fc50a8c");
    }

    //6
    @Test
    void testEmailNonValida() {
        assertUtenteNonValido("emailSbagliata", "alessietto", "Centonzetto",
                TipoUtente.ADMIN, "ec56d024-7520-4829-a760-faa14fc50a8c");
    }

    //7
    @Test
    void testNomeNull() {
        assertUtenteNonValido("Antonio.desantis2004@gmail.com", null, "De Santis",
                TipoUtente.CLIENTE, null);
    }

    //8
    @Test
    void testNomeVuoto() {
        assertUtenteNonValido("antonio.desantis2004@inwind.it", "", "de santis",
                TipoUtente.AGENTE, "ec56d024-7520-4829-a760-faa14fc50a8c");
    }

    //9
    @Test
    void testNomeInvalido() {
        assertUtenteNonValido("elelo@gmail.com", "111elenoire", "lombari",
                TipoUtente.CLIENTE, null);
    }

    //10
    @Test
    void testCognomeNull() {
        assertUtenteNonValido("elelombi213@alice.com", "elenoire", null,
                TipoUtente.AGENTE, "ec56d024-7520-4829-a760-faa14fc50a8c");
    }

    //11
    @Test
    void testCognomeVuoto() {
        assertUtenteNonValido("elelombi213@alice.com", "elenoire", "",
                TipoUtente.AGENTE, "ec56d024-7520-4829-a760-faa14fc50a8c");
    }

    //12
    @Test
    void testCognomeInvalido() {
        assertUtenteNonValido("elelomb.ari@alix.org", "ele", "lomb_ari",
                TipoUtente.CLIENTE, null);
    }

    //13
    @Test
    void testAgenziaVuota() {
        assertUtenteNonValido("elelomb.ari@alix.org", "ele", "lombari",
                TipoUtente.AGENTE, "");
    }

    //14
    @Test
    void testAgenziaInvalida() {
        assertUtenteNonValido("elelomb.ari@alix.org", "ele", "lombari",
                TipoUtente.ADMIN, "idsbagliato76");
    }
}