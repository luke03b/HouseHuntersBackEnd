package com.househuntersbackend.demo.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DateUtils {

    private DateUtils() {}

    public static Set<LocalDate> getFestivitaItaliane() {
        int annoCorrente = LocalDate.now().getYear();
        Set<LocalDate> festivita = new HashSet<>();

        festivita.add(LocalDate.of(annoCorrente, 1, 1));   // Capodanno
        festivita.add(LocalDate.of(annoCorrente, 1, 6));   // Epifania
        festivita.add(LocalDate.of(annoCorrente, 4, 25));  // Festa della Liberazione
        festivita.add(LocalDate.of(annoCorrente, 5, 1));   // Festa dei Lavoratori
        festivita.add(LocalDate.of(annoCorrente, 6, 2));   // Festa della Repubblica
        festivita.add(LocalDate.of(annoCorrente, 8, 15));  // Ferragosto
        festivita.add(LocalDate.of(annoCorrente, 11, 1));  // Ognissanti
        festivita.add(LocalDate.of(annoCorrente, 12, 8));  // Immacolata Concezione
        festivita.add(LocalDate.of(annoCorrente, 12, 25)); // Natale
        festivita.add(LocalDate.of(annoCorrente, 12, 26)); // Santo Stefano

        // Aggiungere Pasqua e Pasquetta (calcolate dinamicamente)
        LocalDate pasqua = calcolaPasqua(annoCorrente);
        festivita.add(pasqua);               // Pasqua
        festivita.add(pasqua.plusDays(1));   // Pasquetta

        return festivita;
    }

    /**
     * Calcola la data della Pasqua per un dato anno (Algoritmo di Meeus).
     */
    private static LocalDate calcolaPasqua(int anno) {
        int a = anno % 19;
        int b = anno / 100;
        int c = anno % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mese = (h + l - 7 * m + 114) / 31;
        int giorno = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(anno, mese, giorno);
    }

    public static LocalDate assegnaDataNonFestiva(LocalDate dataProva, boolean aggiungiGiorni) {
        if(dataProva.getDayOfWeek() == DayOfWeek.SUNDAY || DateUtils.getFestivitaItaliane().contains(dataProva)) {
            if(aggiungiGiorni) {
                dataProva = dataProva.plusDays(1);
            } else {
                dataProva = dataProva.minusDays(1);
            }
            dataProva = assegnaDataNonFestiva(dataProva, aggiungiGiorni);
        }
        return dataProva;
    }

    public static LocalDate assegnaDomenica(LocalDate dataProva, boolean aggiungiGiorni) {
        if(dataProva.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if(aggiungiGiorni) {
                dataProva = dataProva.plusDays(1);
            } else {
                dataProva = dataProva.minusDays(1);
            }
            dataProva = assegnaDomenica(dataProva, aggiungiGiorni);
        }
        return dataProva;
    }
}