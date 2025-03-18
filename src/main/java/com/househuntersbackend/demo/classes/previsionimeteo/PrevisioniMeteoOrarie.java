package com.househuntersbackend.demo.classes.previsionimeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PrevisioniMeteoOrarie {
    // Getters e Setters
    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperatura2m;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

}