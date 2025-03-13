package com.househuntersbackend.demo.exceptions;

public class DataNonValidaException extends RuntimeException {
    public DataNonValidaException(String message) {
        super(message);
    }
}
