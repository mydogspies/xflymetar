package com.mydogspies.xflymetar.parser;

import android.util.Log;

import com.mydogspies.xflymetar.MainActivity;
import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.apis.PojoMetar;
import com.mydogspies.xflymetar.apis.PojoTaf;
import com.mydogspies.xflymetar.data.Airport;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Parses the raw pojos from the API into something nicer.
 * In future versions validation will end up here! // TODO this!
 * @author github.com/mydogspies.com
 * @since 0.1.0
 */
public class RawAPIDataParser {

    // TODO some nice formatting of the single data entries should happen to too
    // TODO add error checks

    private Map<VIEWSTATE, Object> currentAPIData = MainActivity.currentAPIData;

    public Metar parseMetarPojo(VIEWSTATE state, PojoMetar metar) {
        boolean apiError = metar.isApiError();
        Metar metarObject = new Metar(state, metar.getStation_id(), new Date());

        if (!apiError) {
            // TODO set type already in PojoMetar!
            metarObject.setRawText(metar.getRaw_text());
            metarObject.setObservationTime(metar.getObservation_time());
            metarObject.setLatitude(metar.getLatitude());
            metarObject.setLongitude(metar.getLongitude());
            metarObject.setTempC(metar.getTemp_c());
            metarObject.setDewpointC(metar.getDewpoint_c());
            metarObject.setWindDirDegrees(metar.getWind_dir_degrees());
            metarObject.setWindSpeedKt(metar.getWind_speed_kt());
            metarObject.setVisibilityStatuteMi(metar.getVisibility_statute_mi());
            metarObject.setAltimHg(metar.getAltim_in_hg());
            metarObject.setSkyCover(metar.getSky_cover());
            metarObject.setCloudBaseFtAgl(metar.getCloud_base_ft_agl());
            metarObject.setFlightCategory(metar.getFlight_category());
            metarObject.setElevationMeters(metar.getElevation_m());
            currentAPIData.replace(state, metarObject);
            return metarObject;
        }
        return null;
        // TODO IMPORTANT! Deal with empty objects!
    }

    public Taf parseTafPojo(VIEWSTATE state, PojoTaf taf) {

        // TODO add error checks

        int numResults = taf.getNum_results();
        Instant timeNow = Instant.now();
        boolean apiError = taf.isApiError();
        List<PojoTaf.Forecast> forecastList = taf.getForecast();
        Taf tafObject = new Taf(state, taf.getStation_id(), new Date());

        if(!apiError) {
            tafObject.setRawText(taf.getRaw_text());
            tafObject.setIssueTime(taf.getIssue_time());
            tafObject.setBulletinTime(taf.getBulletin_time());
            tafObject.setValidTimeFrom(taf.getValid_time_from());
            tafObject.setValidTimeTo(taf.getValid_time_to());
            tafObject.setLatitude(taf.getLatitude());
            tafObject.setLongitude(taf.getLongitude());
            tafObject.setElevationMeters(taf.getElevation_m());

            if (numResults != 0) {
                for (PojoTaf.Forecast cast : forecastList) {
                    String fcstTimeTo = cast.getFcst_time_to();
                    Instant timeToInsta = Instant.parse(fcstTimeTo);
                    if (timeNow.compareTo(timeToInsta) < 0) {
                        tafObject.setForecastTimeFrom(cast.getFcst_time_from());
                        Log.i("Xflymetar: (TAF) Forecast_Time_To", cast.getFcst_time_to());
                        tafObject.setForecastTimeTo(cast.getFcst_time_to());
                        tafObject.setWindDirDegrees(cast.getWind_dir_degrees());
                        tafObject.setWindSpeedKt(cast.getWind_speed_kt());
                        tafObject.setWindGustKt(cast.getWind_gust_kt());
                        tafObject.setVisibilityStatuteMiles(cast.getVisibility_statute_mi());
                        tafObject.setChangeIndicator(cast.getChange_indicator());
                        tafObject.setWxString(cast.getWx_string());
                        tafObject.setSkyCover(cast.getSky_cover());
                        tafObject.setCloudBaseFtAgl(cast.getCloud_base_ft_agl());
                        break;
                    }
                }
            }
            currentAPIData.replace(state, tafObject);
            return tafObject;
        }
        return null;
        // TODO IMPORTANT! Deal with empty objects!
    }

    // TODO do we need this method?
    public Airport airportObjectBuilder(VIEWSTATE state, Metar metar, Taf taf) {
        return null;
    }
}
