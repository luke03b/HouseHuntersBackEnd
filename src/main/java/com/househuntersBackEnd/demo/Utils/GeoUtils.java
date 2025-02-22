package com.househuntersBackEnd.demo.Utils;

public class GeoUtils {
    // Costante: 1° di latitudine = ~111.32 km ovunque sulla Terra
    private static final double LATITUDE_KM = 111.32;

    /**
     * Converte una distanza in km nei corrispondenti gradi di latitudine e longitudine.
     *
     * @param latitude   Latitudine del punto centrale
     * @param distanceKm Distanza in km
     * @return Array con [deltaLatitudine, deltaLongitudine]
     */
    public static double[] kmToDegrees(double latitude, double distanceKm) {
        // 1° di longitudine varia con il coseno della latitudine
        double longitudeKm = LATITUDE_KM * Math.cos(Math.toRadians(latitude));

        // Calcola la variazione in gradi
        double deltaLat = distanceKm / LATITUDE_KM;
        double deltaLon = distanceKm / longitudeKm;

        return new double[]{deltaLat, deltaLon};
    }
}
