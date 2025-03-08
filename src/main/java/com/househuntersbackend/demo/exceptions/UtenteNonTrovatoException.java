package com.househuntersbackend.demo.exceptions;

public class UtenteNonTrovatoException extends RuntimeException {
    public UtenteNonTrovatoException() {
        super("Utente non trovato");
    }
}
