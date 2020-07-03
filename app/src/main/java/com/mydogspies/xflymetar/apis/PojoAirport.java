package com.mydogspies.xflymetar.apis;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Retrofit Pojo for the airport data from api.flightplandatabase.com
 * The string dataType denotes which API we got data from. In this case it should be "airport".
 *
 * The boolean apiError is set to TRUE if we fail to get a response as a flag since the object is
 * always passed on no matter what. If API response was successful then it should be FALSE.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
public class PojoAirport {

    private String dataType = "airport";
    private boolean apiError = false;

    @SerializedName("name")
    private String name;

    @SerializedName("ICAO")
    private String icao;

    @SerializedName("timezone")
    private Timezone timezone;

    @SerializedName("times")
    private Times times;

    @SerializedName("runwayCount")
    private int runwayCount;

    @SerializedName("runways")
    private List<Runway> runways;

    @SerializedName("frequencies")
    private List<Frequencies> frequencies;

    public String getIcao() {
        return icao;
    }

    public List<Frequencies> getFrequencies() {
        return frequencies;
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public int getRunwayCount() {
        return runwayCount;
    }

    public Times getTimes() {
        return times;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setApiError(boolean apiError) {
        this.apiError = apiError;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isApiError() {
        return apiError;
    }

    public String getName() {
        return name;
    }

    /* timezone element */
    public static class Timezone {

        @SerializedName("offset")
        private String offset;

        public String getOffset() {
            return offset;
        }
    }

    /* times element */
    public static class Times {

        @SerializedName("sunrise")
        private String sunrise;

        @SerializedName("sunset")
        private String sunset;

        public String getSunrise() {
            return sunrise;
        }

        public String getSunset() {
            return sunset;
        }
    }

    /* runways element */
    public static class Runway {

        @SerializedName("ident")
        private String ident;

        @SerializedName("width")
        private double width;

        @SerializedName("length")
        private double length;

        @SerializedName("bearing")
        private double bearing;

        @SerializedName("navaids")
        private List<Navaids> navaids;

        public List<Navaids> getNavaids() {
            return navaids;
        }

        public String getIdent() {
            return ident;
        }

        public double getWidth() {
            return width;
        }

        public double getLength() {
            return length;
        }

        public double getBearing() {
            return bearing;
        }
    }

    /* runway/navaids element */
    public static class Navaids {

        @SerializedName("type")
        private String type;

        @SerializedName("range")
        private double range;

        public String getType() {
            return type;
        }

        public double getRange() {
            return range;
        }
    }

    /* frequencies element */
    public static class Frequencies {

        @SerializedName("type")
        private String type;

        @SerializedName("frequency")
        private int frequency;

        @SerializedName("name")
        private String name;

        public String getType() {
            return type;
        }

        public int getFrequency() {
            return frequency;
        }

        public String getName() {
            return name;
        }
    }


}
