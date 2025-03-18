package com.househuntersbackend.demo.classes.previsionimeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PrevisioniMeteoGiornaliere {
    private List<String> time;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperaturaMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperaturaMin;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

    public List<String> getTime() { return time; }
    public void setTime(List<String> time) { this.time = time; }

    public List<Double> getTemperaturaMax() { return temperaturaMax; }
    public void setTemperaturaMax(List<Double> temperaturaMax) { this.temperaturaMax = temperaturaMax; }

    public List<Double> getTemperaturaMin() { return temperaturaMin; }
    public void setTemperaturaMin(List<Double> temperaturaMin) { this.temperaturaMin = temperaturaMin; }

    public List<Integer> getWeatherCode() { return weatherCode; }
    public void setWeatherCode(List<Integer> weatherCode) { this.weatherCode = weatherCode; }
}
