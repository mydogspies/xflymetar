package com.mydogspies.xflymetar.data;

import com.mydogspies.xflymetar.VIEWSTATE;

import java.util.Date;

/**
 * The internal TAF pojo used to store the cleaned/parsed data and distribute it throughout
 * the observer network.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
public class Taf implements AirportCodeIO {

    private Date timeStamp;
    private VIEWSTATE viewState;

    private String rawText;
    private String stationID;
    private String issueTime;
    private String bulletinTime;
    private String validTimeFrom;
    private String validTimeTo;
    private double latitude;
    private double longitude;
    private double elevationMeters;
    private String forecastTimeFrom;
    private String forecastTimeTo;
    private int windDirDegrees;
    private int windSpeedKt;
    private int windGustKt;
    private float visibilityStatuteMiles;
    private String changeIndicator;
    private String wxString;
    private String skyCover;
    private int cloudBaseFtAgl;

    public Taf(VIEWSTATE state, String stationID, Date date) {
        this.viewState = state;
        this.stationID = stationID;
        this.timeStamp = date;
    }

    @Override
    public String getIcaoCode() {
        return getStationID();
    }

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

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getBulletinTime() {
        return bulletinTime;
    }

    public void setBulletinTime(String bulletinTime) {
        this.bulletinTime = bulletinTime;
    }

    public String getValidTimeFrom() {
        return validTimeFrom;
    }

    public void setValidTimeFrom(String validTimeFrom) {
        this.validTimeFrom = validTimeFrom;
    }

    public String getValidTimeTo() {
        return validTimeTo;
    }

    public void setValidTimeTo(String validTimeTo) {
        this.validTimeTo = validTimeTo;
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

    public double getElevationMeters() {
        return elevationMeters;
    }

    public void setElevationMeters(double elevationMeters) {
        this.elevationMeters = elevationMeters;
    }

    public String getForecastTimeFrom() {
        return forecastTimeFrom;
    }

    public void setForecastTimeFrom(String forecastTimeFrom) {
        this.forecastTimeFrom = forecastTimeFrom;
    }

    public String getForecastTimeTo() {
        return forecastTimeTo;
    }

    public void setForecastTimeTo(String forecastTimeTo) {
        this.forecastTimeTo = forecastTimeTo;
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

    public void setWindSpeedKt(int windSpeed_Kt) {
        this.windSpeedKt = windSpeed_Kt;
    }

    public int getWindGustKt() {
        return windGustKt;
    }

    public void setWindGustKt(int windGustKt) {
        this.windGustKt = windGustKt;
    }

    public float getVisibilityStatuteMiles() {
        return visibilityStatuteMiles;
    }

    public void setVisibilityStatuteMiles(float visibilityStatuteMiles) {
        this.visibilityStatuteMiles = visibilityStatuteMiles;
    }

    public String getChangeIndicator() {
        return changeIndicator;
    }

    public void setChangeIndicator(String changeIndicator) {
        this.changeIndicator = changeIndicator;
    }

    public String getWxString() {
        return wxString;
    }

    public void setWxString(String wxString) {
        this.wxString = wxString;
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
}
