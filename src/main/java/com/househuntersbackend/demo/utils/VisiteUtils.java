package com.househuntersbackend.demo.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisiteUtils {
    public static boolean isVisitaValida(LocalDate dataVisita, LocalTime orarioInizioVisita, LocalTime orarioFineVisita) {
        boolean isVisitaValida;
        isVisitaValida = dataVisita.isAfter(LocalDate.now()) && dataVisita.isBefore(LocalDate.now().plusDays(15));
        isVisitaValida &= orarioInizioVisita.isAfter(LocalTime.of(8, 59)) && orarioInizioVisita.isBefore(LocalTime.of(17, 1));
        isVisitaValida &= orarioFineVisita.isAfter(LocalTime.of(9, 59)) && orarioFineVisita.isBefore(LocalTime.of(18, 1));
        return isVisitaValida;
    }
}
