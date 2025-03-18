package com.househuntersbackend.demo.classes.previsionimeteo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrevisioniMeteo {
    private double latitude;
    private double longitude;
    private String timezone;
    private double elevation;
    private PrevisioniMeteoOrarie hourly;
    private PrevisioniMeteoGiornaliere daily;

}