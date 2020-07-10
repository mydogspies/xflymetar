package com.mydogspies.xflymetar.data;

import com.mydogspies.xflymetar.VIEWSTATE;

import java.util.Date;

/**
 * The internal AIRPORT pojo used to store the cleaned/parsed data and distribute it throughout
 * the observer network.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
public class Airport {

    private long timeStamp;
    private VIEWSTATE viewState;

    private Metar metarData;
    private Taf tafData;

    private String iataCode;
    private String icaoCode;
    private String airportName;

    public Airport(VIEWSTATE state, String icaoCode, Metar metar, Taf taf) {
        this.viewState = state;
        this.icaoCode = icaoCode;
        this.tafData = taf;
        this.metarData = metar;
        this.timeStamp = new Date().getTime();
    }

    /* GETTERS and SETTERS */

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public VIEWSTATE getViewState() {
        return viewState;
    }

    public void setViewState(VIEWSTATE viewState) {
        this.viewState = viewState;
    }

    public Metar getMetarData() {
        return metarData;
    }

    public void setMetarData(Metar metarData) {
        this.metarData = metarData;
    }

    public Taf getTafData() {
        return tafData;
    }

    public void setTafData(Taf tafData) {
        this.tafData = tafData;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
