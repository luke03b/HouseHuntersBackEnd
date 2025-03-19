package com.househuntersbackend.demo.utils;

import com.househuntersbackend.demo.enumerations.StatoVisita;
import org.springframework.stereotype.Component;

@Component
public class VisitaUtils {

    private VisitaUtils() {}

    public static StatoVisita mappaStatoVisita(String statoVisita) {
        if(statoVisita.equals(StatoVisita.CONFERMATA.toString().toUpperCase())) {
            return StatoVisita.CONFERMATA;
        } else if (statoVisita.equals(StatoVisita.IN_ATTESA.toString().toUpperCase())) {
            return StatoVisita.IN_ATTESA;
        } else if (statoVisita.equals(StatoVisita.RIFIUTATA.toString().toUpperCase())) {
            return StatoVisita.RIFIUTATA;
        }
        return null;
    }
}
