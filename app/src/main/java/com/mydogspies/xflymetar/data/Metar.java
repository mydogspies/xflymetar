package com.mydogspies.xflymetar.data;

import com.mydogspies.xflymetar.VIEWSTATE;

import java.util.Date;

public class Metar implements AirportCodeIO{

    private Date timeStamp;
    private VIEWSTATE viewState;

    private String rawText;
    private String stationID;
    private String observationTime;
    private double latitude;
    private double longitude;
    private float tempC;
    private float dewpointC;
    private int windDirDegrees;
    private int windSpeedKt;
    private float visibilityStatuteMi;
    private double altimHg;
    private String skyCover;
    private int cloudBaseFtAgl;
    private String flightCategory;
    private double elevationMeters;

    public Metar(VIEWSTATE state, String stationID, Date timeStamp) {
        this.viewState = state;
        this.stationID = stationID;
        this.timeStamp = timeStamp;
    }

    @Override
    public String getIcaoCode() {
        return getStationID();
    }

    /* GETTERS and SETTERS */

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public VIEWSTATE getViewState() {
        return viewState;
    }

    public void setViewState(VIEWSTATE viewState) {
        this.viewState = viewState;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getTempC() {
        return tempC;
    }

    public void setTempC(float tempC) {
        this.tempC = tempC;
    }

    public float getDewpointC() {
        return dewpointC;
    }

    public void setDewpointC(float dewpointC) {
        this.dewpointC = dewpointC;
    }

    public int getWindDirDegrees() {
        return windDirDegrees;
    }

    public void setWindDirDegrees(int windDirDegrees) {
        this.windDirDegrees = windDirDegrees;
    }

    public int getWindSpeedKt() {
        return windSpeedKt;
    }

    public void setWindSpeedKt(int windSpeedKt) {
        this.windSpeedKt = windSpeedKt;
    }

    public float getVisibilityStatuteMi() {
        return visibilityStatuteMi;
    }

    public void setVisibilityStatuteMi(float visibilityStatuteMi) {
        this.visibilityStatuteMi = visibilityStatuteMi;
    }

    public double getAltimHg() {
        return altimHg;
    }

    public void setAltimHg(double altimHg) {
        this.altimHg = altimHg;
    }

    public String getSkyCover() {
        return skyCover;
    }

    public void setSkyCover(String skyCover) {
        this.skyCover = skyCover;
    }

    public int getCloudBaseFtAgl() {
        return cloudBaseFtAgl;
    }

    public void setCloudBaseFtAgl(int cloudBaseFtAgl) {
        this.cloudBaseFtAgl = cloudBaseFtAgl;
    }

    public String getFlightCategory() {
        return flightCategory;
    }

    public void setFlightCategory(String flightCategory) {
        this.flightCategory = flightCategory;
    }

    public double getElevationMeters() {
        return elevationMeters;
    }

    public void setElevationMeters(double elevationMeters) {
        this.elevationMeters = elevationMeters;
    }
}
