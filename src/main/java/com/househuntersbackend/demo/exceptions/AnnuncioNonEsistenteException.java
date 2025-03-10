package com.househuntersbackend.demo.exceptions;

public class AnnuncioNonEsistenteException extends RuntimeException {
    public AnnuncioNonEsistenteException(String message) {
        super(message);
    }
}
