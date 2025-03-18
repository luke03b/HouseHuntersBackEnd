package com.househuntersbackend.demo.classes.previsionimeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PrevisioniMeteoOrarie {
    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperatura2m;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

    // Getters e Setters
    public List<String> getTime() { return time; }
    public void setTime(List<String> time) { this.time = time; }

    public List<Double> getTemperatura2m() { return temperatura2m; }
    public void setTemperatura2m(List<Double> temperatura2m) { this.temperatura2m = temperatura2m; }

    public List<Integer> getWeatherCode() { return weatherCode; }
    public void setWeatherCode(List<Integer> weatherCode) { this.weatherCode = weatherCode; }
}
