package com.househuntersbackend.demo.classes.annuncio;

public class IntervalloStanze {
    private final Integer numStanzeMinime;
    private final Integer numStanzeMassime;

    public IntervalloStanze(Integer numStanzeMinime, Integer numStanzeMassime) {
        this.numStanzeMinime = numStanzeMinime;
        this.numStanzeMassime = numStanzeMassime;
    }

    public Integer getNumStanzeMinime() {
        return numStanzeMinime;
    }

    public Integer getNumStanzeMassime() {
        return numStanzeMassime;
    }
}
