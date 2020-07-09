package com.mydogspies.xflymetar.apis;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * The Retrofit Pojo for the TAF data from aviationweather.gov.
 * The string dataType denotes which API we got data from. In this case it should be "taf".
 *
 * The boolean apiError is set to TRUE if we fail to get a response as a flag since the object is
 * always passed on no matter what. If API response was successful then it should be FALSE.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
@Root(strict = false)
public class PojoTaf {

    private String dataType = "taf";
    private boolean apiError = false;

    @Path("data")
    @Attribute
    private int num_results;

    @Path("data/TAF")
    @Element
    private String raw_text;

    @Path("data/TAF")
    @Element
    private String station_id;

    @Path("data/TAF")
    @Element
    private String issue_time;

    @Path("data/TAF")
    @Element
    private String bulletin_time;

    @Path("data/TAF")
    @Element
    private String valid_time_from;

    @Path("data/TAF")
    @Element
    private String valid_time_to;

    @Path("data/TAF")
    @Element
    private double latitude;

    @Path("data/TAF")
    @Element
    private double longitude;

    @Path("data/TAF")
    @Element
    private double elevation_m;

    @Path("data/TAF")
    @ElementList(inline = true)
    private List<Forecast> forecast;

    public String getDataType() {
        return dataType;
    }

    public void setApiError(boolean apiError) {
        this.apiError = apiError;
    }

    public boolean isApiError() {
        return apiError;
    }

    public int getNum_results() {
        return num_results;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public String getStation_id() {
        return station_id;
    }

    public String getIssue_time() {
        return issue_time;
    }

    public String getBulletin_time() {
        return bulletin_time;
    }

    public String getValid_time_from() {
        return valid_time_from;
    }

    public String getValid_time_to() {
        return valid_time_to;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation_m() {
        return elevation_m;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    /* forecast element */

    @Root(strict = false)
    public static class Forecast {

        @Element
        private String fcst_time_from;

        @Element
        private String fcst_time_to;

        @Element(required = false)
        private int wind_dir_degrees;

        @Element(required = false)
        private int wind_speed_kt;

        @Element(required = false)
        private int wind_gust_kt;

        @Element(required = false)
        private float visibility_statute_mi;

        @Element(required = false)
        private String change_indicator;

        @Element(required = false)
        private String wx_string;

        @Path("sky_condition")
        @Attribute(required = false)
        private String sky_cover;

        @Path("sky_condition")
        @Attribute(required = false)
        private int cloud_base_ft_agl;

        public String getFcst_time_from() {
            return fcst_time_from;
        }

        public String getFcst_time_to() {
            return fcst_time_to;
        }

        public int getWind_dir_degrees() {
            return wind_dir_degrees;
        }

        public int getWind_speed_kt() {
            return wind_speed_kt;
        }

        public int getWind_gust_kt() {
            return wind_gust_kt;
        }

        public float getVisibility_statute_mi() {
            return visibility_statute_mi;
        }

        public String getChange_indicator() {
            return change_indicator;
        }

        public String getWx_string() {
            return wx_string;
        }

        public String getSky_cover() {
            return sky_cover;
        }

        public int getCloud_base_ft_agl() {
            return cloud_base_ft_agl;
        }
    }
}
