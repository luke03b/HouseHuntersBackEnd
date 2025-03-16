package com.househuntersbackend.demo.utils;

import com.househuntersbackend.demo.entities.Offerte;
import com.househuntersbackend.demo.enumerations.StatoOfferta;
import org.springframework.stereotype.Component;

@Component
public class OffertaUtils {

    private OffertaUtils() {}

    public static StatoOfferta mappaStatoOfferta(String statoOfferta) {
        if(statoOfferta.equals(StatoOfferta.CONTROPROPOSTA.toString())) {
            return StatoOfferta.CONTROPROPOSTA;
        } else if (statoOfferta.equals(StatoOfferta.IN_ATTESA.toString())){
            return StatoOfferta.IN_ATTESA;
        } else if (statoOfferta.equals(StatoOfferta.ACCETTATA.toString())){
            return StatoOfferta.ACCETTATA;
        } else {
            return StatoOfferta.RIFIUTATA;
        }
    }

    public static boolean isOffertaManuale(Offerte offerta) {
        return offerta.getCliente() == null;
    }
}
