package com.househuntersbackend.demo.classes.previsionimeteo;

public class PrevisioniMeteo {
    private double latitude;
    private double longitude;
    private String timezone;
    private double elevation;
    private PrevisioniMeteoOrarie hourly;
    private PrevisioniMeteoGiornaliere daily;

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public double getElevation() { return elevation; }
    public void setElevation(double elevation) { this.elevation = elevation; }

    public PrevisioniMeteoOrarie getHourly() { return hourly; }
    public void setHourly(PrevisioniMeteoOrarie hourly) { this.hourly = hourly; }

    public PrevisioniMeteoGiornaliere getDaily() { return daily; }
    public void setDaily(PrevisioniMeteoGiornaliere daily) { this.daily = daily; }
}
