package com.househuntersbackend.demo.classes.annuncio;

public class Coordinate {
    private final Double latitudine;
    private final Double longitudine;

    public Coordinate(Double latitudine, Double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }


    public Double getLatitudine() {
        return latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }
}
