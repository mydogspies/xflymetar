package com.mydogspies.xflymetar.apis;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * The Retrofit Pojo for the METAR data from aviationweather.gov.
 * The string dataType denotes which API we got data from. In this case it should be "metar".
 *
 * The boolean apiError is set to TRUE if we fail to get a response as a flag since the object is
 * always passed on no matter what. If API response was successful then it should be FALSE.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
@Root(strict = false)
public class PojoMetar {

    private String dataType = "metar";
    private boolean apiError = false;

    @Path("data/METAR")
    @Element
    private String raw_text;

    @Path("data/METAR")
    @Element
    private String station_id;

    @Path("data/METAR")
    @Element
    private String observation_time;

    @Path("data/METAR")
    @Element
    private String latitude;

    @Path("data/METAR")
    @Element
    private String longitude;

    @Path("data/METAR")
    @Element
    private String temp_c;

    @Path("data/METAR")
    @Element
    private String dewpoint_c;

    @Path("data/METAR")
    @Element
    private String wind_dir_degrees;

    @Path("data/METAR")
    @Element
    private String wind_speed_kt;

    @Path("data/METAR")
    @Element
    private String visibility_statute_mi;

    @Path("data/METAR")
    @Element
    private String altim_in_hg;

    @Path("data/METAR/sky_condition")
    @Attribute
    private String sky_cover;

    @Path("data/METAR/sky_condition")
    @Attribute
    private String cloud_base_ft_agl;

    @Path("data/METAR")
    @Element
    private String flight_category;

    @Path("data/METAR")
    @Element
    private String elevation_m;

    public boolean isApiError() {
        return apiError;
    }

    public void setApiError(boolean apiError) {
        this.apiError = apiError;
    }

    public String getDataType() {
        return dataType;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public String getStation_id() {
        return station_id;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public String getDewpoint_c() {
        return dewpoint_c;
    }

    public String getWind_dir_degrees() {
        return wind_dir_degrees;
    }

    public String getWind_speed_kt() {
        return wind_speed_kt;
    }

    public String getVisibility_statute_mi() {
        return visibility_statute_mi;
    }

    public String getAltim_in_hg() {
        return altim_in_hg;
    }

    public String getSky_cover() {
        return sky_cover;
    }

    public String getCloud_base_ft_agl() {
        return cloud_base_ft_agl;
    }

    public String getFlight_category() {
        return flight_category;
    }

    public String getElevation_m() {
        return elevation_m;
    }
}




