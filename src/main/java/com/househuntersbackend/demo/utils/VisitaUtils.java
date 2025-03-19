package com.househuntersbackend.demo.utils;

import com.househuntersbackend.demo.enumerations.StatoVisita;
import org.springframework.stereotype.Component;

@Component
public class VisitaUtils {

    private VisitaUtils() {}

    public static StatoVisita mappaStatoVisita(String statoVisita) {
        if(statoVisita.equals(StatoVisita.ACCETTATA.toString())) {
            return StatoVisita.ACCETTATA;
        } else if (statoVisita.equals(StatoVisita.IN_ATTESA.toString())){
            return StatoVisita.IN_ATTESA;
        } else {
            return StatoVisita.RIFIUTATA;
        }
    }
}
