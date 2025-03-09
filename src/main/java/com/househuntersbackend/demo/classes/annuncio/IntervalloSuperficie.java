package com.househuntersbackend.demo.classes.annuncio;

public class IntervalloSuperficie {
    private final Integer superficieMinima;
    private final Integer superficieMassima;

    public IntervalloSuperficie(Integer superficieMinima, Integer superficieMassima) {
        this.superficieMinima = superficieMinima;
        this.superficieMassima = superficieMassima;
    }

    public Integer getSuperficieMinima() {
        return superficieMinima;
    }

    public Integer getSuperficieMassima() {
        return superficieMassima;
    }
}
