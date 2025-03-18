package com.househuntersbackend.demo.classes.previsionimeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PrevisioniMeteoGiornaliere {
    private List<String> time;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperaturaMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperaturaMin;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

}