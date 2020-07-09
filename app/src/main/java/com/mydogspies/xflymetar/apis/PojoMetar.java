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
    private double latitude;

    @Path("data/METAR")
    @Element
    private double longitude;

    @Path("data/METAR")
    @Element
    private float temp_c;

    @Path("data/METAR")
    @Element
    private float dewpoint_c;

    @Path("data/METAR")
    @Element
    private int wind_dir_degrees;

    @Path("data/METAR")
    @Element
    private int wind_speed_kt;

    @Path("data/METAR")
    @Element
    private float visibility_statute_mi;

    @Path("data/METAR")
    @Element
    private double altim_in_hg;

    @Path("data/METAR/sky_condition")
    @Attribute
    private String sky_cover;

    @Path("data/METAR/sky_condition")
    @Attribute(required = false)
    private int cloud_base_ft_agl;

    @Path("data/METAR")
    @Element
    private String flight_category;

    @Path("data/METAR")
    @Element
    private double elevation_m;

    public String getDataType() {
        return dataType;
    }

    public void setApiError(boolean apiError) {
        this.apiError = apiError;
    }

    public boolean isApiError() {
        return apiError;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getTemp_c() {
        return temp_c;
    }

    public float getDewpoint_c() {
        return dewpoint_c;
    }

    public int getWind_dir_degrees() {
        return wind_dir_degrees;
    }

    public int getWind_speed_kt() {
        return wind_speed_kt;
    }

    public float getVisibility_statute_mi() {
        return visibility_statute_mi;
    }

    public double getAltim_in_hg() {
        return altim_in_hg;
    }

    public String getSky_cover() {
        return sky_cover;
    }

    public int getCloud_base_ft_agl() {
        return cloud_base_ft_agl;
    }

    public String getFlight_category() {
        return flight_category;
    }

    public double getElevation_m() {
        return elevation_m;
    }
}




