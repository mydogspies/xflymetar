package com.mydogspies.xflymetar.apis;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * This class uses Retrofit and the Simple XML serialization network in order to grab Metar, Taf and Airport data.
 * It uses Retrofit-style pojos, one for each API call, and serializes them into objects. Each call
 * uses a separate thread as defined by the call.enqueue method and returns each object into the
 * setData() method which waits for all responses, handles global timeout and feeds the observers.
 * NOTE: This class is the Subject of the Observer network and all observers must subscribe to it
 * using the GetXMLDataSingleton method. See MainActivity in the init() method for basic example.
 * @author github.com/mydogspies
 * @since 0.1.0
 * @see PojoMetar
 * @see PojoTaf
 * @see PojoAirport
 * @see GetXMLDataSingleton
 */
public class GetXMLData implements APIDataIO {

    private final List<DataObserverIO> observers = new ArrayList<>();

    /* Interfaces for Retrofit */

    interface APIMetar {
        @GET
        Call<PojoMetar> getAWData(@Url String airportCode);
    }

    interface APITaf {
        @GET
        Call<PojoTaf> getAWData(@Url String airportCode);
    }

    /* API handler methods */

    /**
     * Grabs the METAR data using airport code as argument.
     * Returns a data object to the setData() method. Note that in case of error
     * the method still sends an object with the correct instance variable set to flag an error.
     * @param airportCode single airport ICAO code
     */
    @Override
    public void getMetarAsObject(String airportCode) {

        String url = "adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&hoursBeforeNow=.5&stationString=";

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        APIMetar con = retro.create(APIMetar.class);
        Call<PojoMetar> call = con.getAWData(url + airportCode);

        call.enqueue(new Callback<PojoMetar>() {
            @Override
            public void onResponse(Call<PojoMetar> call, Response<PojoMetar> response) {
                Log.i("msginfo: HTTP RESPONSE:", String.valueOf(response.code()));
                setMetarData(response.body());
            }

            @Override
            public void onFailure(Call<PojoMetar> call, Throwable t) {
                Log.e("msginfo: HTTP RESPONSE ERROR:", t.getMessage());
                PojoMetar data = new PojoMetar();
                data.setApiError(true);
                setMetarData(data);
                // TODO implement proper error handling
            }
        });
    }

    /**
     * Grabs the TAF data using airport code as argument.
     * Returns a data object to the setData() method. Note that in case of error
     * the method still sends an object with the correct instance variable set to flag an error.
     * @param airportCode single airport ICAO code
     */
    @Override
    public void getTafAsObject(String airportCode) {

        String url = "adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&hoursBeforeNow=0&stationString=";

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        APITaf con = retro.create(APITaf.class);
        Call<PojoTaf> call = con.getAWData(url + airportCode);

        call.enqueue(new Callback<PojoTaf>() {
            @Override
            public void onResponse(Call<PojoTaf> call, Response<PojoTaf> response) {
                Log.i("msginfo: HTTP RESPONSE:", String.valueOf(response.code()));
                setTafData(response.body());
            }

            @Override
            public void onFailure(Call<PojoTaf> call, Throwable t) {
                Log.e("msginfo: HTTP RESPONSE ERROR:", t.getMessage());
                PojoTaf data = new PojoTaf();
                data.setApiError(true);
                setTafData(data);
                // TODO implement proper error handling
            }
        });

    }

    /**
     * Grabs the AIRPORT data using airport code as argument.
     * Returns a data object to the setData() method. Note that in case of error
     * the method still sends an object with the correct instance variable set to flag an error.
     * @param airportCode single airport ICAO code
     */
    @Override
    public void getAirportAsObject(String airportCode) {

    }

    /* The observable methods */

    public void addObserver(DataObserverIO dataio) {
        this.observers.add(dataio);
    }

    public void removeObserver(DataObserverIO dataio) {
        this.observers.remove(dataio);
    }

    /**
     * Feeds the METAR data object to the observers
     * @param data the incoming object with API metar data
     */
    public void setMetarData(PojoMetar data) {

        for (DataObserverIO dataio : this.observers) {
            dataio.updateMetarFromAPI(data);
        }
    }

    /**
     * Feeds the TAF data object to the observers
     * @param data the incoming object with API taf data
     */
    public void setTafData(PojoTaf data) {

        for (DataObserverIO dataio : this.observers) {
            dataio.updateTafFromAPI(data);
        }
    }

    /**
     * Feeds the AIRPORT data object to the observers
     * @param data the incoming object with API airport data
     */
    public void setMetarData(PojoAirport data) {

        for (DataObserverIO dataio : this.observers) {
            dataio.updateAirportFromAPI(data);
        }
    }
}
