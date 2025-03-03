package com.househuntersBackEnd.demo.Exceptions;

public class UtenteNonTrovatoException extends RuntimeException {
    public UtenteNonTrovatoException() {
        super("Utente non trovato");
    }
}
