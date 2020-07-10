package com.mydogspies.xflymetar;

/**
 * The different data sets that the layout views can hold. These we use to keep track of what data
 * is coming down the pipeline to different fragments.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
public enum VIEWSTATE {
    ARRIVAL_METAR,
    DEPARTURE_METAR,
    ARRIVAL_TAF,
    DEPARTURE_TAF,
    DEPARTURE_AIRPORT_INFO,
    ARRIVAL_AIRPORT_INFO
}
