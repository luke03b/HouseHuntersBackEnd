package com.househuntersbackend.demo.verifiers;

import com.househuntersbackend.demo.enumerations.TipoUtente;
import com.househuntersbackend.demo.exceptions.UtenteNonValidoException;
import org.springframework.stereotype.Component;

@Component
public class UsersVerifier {

    String nomeOCognomeRegex = "^[A-Za-zÀ-ÿ'-]+(?: [A-Za-zÀ-ÿ'-]+)*$";

    public boolean areUserAttributiValidi(String email, String nome, String cognome, TipoUtente tipo, String idAgenziaImmobiliare) throws UtenteNonValidoException {
        if(email == null || email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new UtenteNonValidoException("Email non valida");
        }

        if(nome == null || nome.isEmpty() || !nome.matches(nomeOCognomeRegex)) {
            throw new UtenteNonValidoException("Nome non valido");
        }

        if(cognome == null || cognome.isEmpty() || !cognome.matches(nomeOCognomeRegex)) {
            throw new UtenteNonValidoException("Cognome non valido");
        }


        if(!tipo.equals(TipoUtente.CLIENTE) && (idAgenziaImmobiliare == null || idAgenziaImmobiliare.isEmpty() ||
                !idAgenziaImmobiliare.matches("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"))) {
            throw new UtenteNonValidoException("agenzia non valida");
        }

        if ((tipo.equals(TipoUtente.CLIENTE) && idAgenziaImmobiliare != null)) {
            throw new UtenteNonValidoException("Se l'utente è di tipo CLIENTE non può avere un'agenzia immobiliare, mentre ADMIN o AGENTE devono averne una valida");
        }

        return true;
    }
}
