package com.mydogspies.xflymetar.apis;

import android.util.Log;

import com.mydogspies.xflymetar.MainActivity;
import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;
import com.mydogspies.xflymetar.parser.RawAPIDataParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * This class uses Retrofit and the Simple XML serialization network in order to grab Metar, Taf and Airport data.
 * It uses Retrofit-style pojos, one for each API call, and serializes them into objects. Each call
 * uses a separate thread as defined by the call.enqueue method and returns each object into the
 * setMetar/TafData() method which feeds the observers.
 *
 * NOTE: This class is the Subject of the Observer network and all observers must subscribe to it
 * using the API handler's addObserver() method via a call to the APIDataSingleton.
 * See MetarView.class in the fragments folder for an example.
 *
 * @author github.com/mydogspies
 * @see PojoMetar
 * @see PojoTaf
 * @see PojoAirport
 * @see APIDataSingleton
 * @see com.mydogspies.xflymetar.fragments.MetarView
 * @since 0.1.0
 */
public class APIData implements APIDataIO {

    private final List<DataObserverIO> observers = new ArrayList<>();
    private RawAPIDataParser dataParser = new RawAPIDataParser();
    private Map<VIEWSTATE, Date> timestampMap = MainActivity.apiTimeStamps;
    private Map<VIEWSTATE, Object> currentAPIData = MainActivity.currentAPIData;


    /* INTERFACES FOR RETROFIT */

    interface APIMetar {
        @GET
        Call<PojoMetar> getAPIxml(@Url String airportCode);
    }

    interface APITaf {
        @GET
        Call<PojoTaf> getAPIxml(@Url String airportCode);
    }


    /* API HANDLER METHODS */

    /**
     * Grabs the METAR data using airport code as argument.
     * Returns a data object to the setData() method. Note that in case of error
     * the method still sends an object with the correct instance variable set to flag an error.
     *
     * @param airportCode single airport ICAO code
     */
    @Override
    public void loadMetarFromAPI(VIEWSTATE state, String airportCode) {

        String url = "adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&hoursBeforeNow=1&stationString=" + airportCode;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                .build();

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIMetar con = retro.create(APIMetar.class);
        Call<PojoMetar> call = con.getAPIxml(url);

        call.enqueue(new Callback<PojoMetar>() {
            @Override
            public void onResponse(Call<PojoMetar> call, Response<PojoMetar> response) {
                Log.i("Xflymetar: (METAR) HTTP RESPONSE", String.valueOf(response.code()));
                Log.i("Xflymetar: (METAR) HTTP BODY", String.valueOf(response.body().getRaw_text()));
                setMetarData(state, response.body());
            }

            @Override
            public void onFailure(Call<PojoMetar> call, Throwable t) {
                Log.e("Xflymetar: (METAR) HTTP RESPONSE ERROR", t.getMessage());
                PojoMetar data = new PojoMetar();
                data.setApiError(true);
                setMetarData(state, data);
                // TODO implement proper error handling
            }
        });
    }

    /**
     * Grabs the TAF data using airport code as argument.
     * Returns a data object to the setData() method. Note that in case of error
     * the method still sends an object with the correct instance variable set to flag an error.
     *
     * @param airportCode single airport ICAO code
     */
    @Override
    public void loadTafFromAPI(VIEWSTATE state, String airportCode) {

        String url = "adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&hoursBeforeNow=.5&stationString=" + airportCode;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                .build();

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build();

        APITaf con = retro.create(APITaf.class);
        Call<PojoTaf> call = con.getAPIxml(url);

        call.enqueue(new Callback<PojoTaf>() {
            @Override
            public void onResponse(Call<PojoTaf> call, Response<PojoTaf> response) {
                Log.i("Xflymetar: (TAF) HTTP RESPONSE", String.valueOf(response.code()));
                Log.i("Xflymetar: (TAF) HTTP BODY", String.valueOf(response.body().getRaw_text()));
                setTafData(state, response.body());
            }

            @Override
            public void onFailure(Call<PojoTaf> call, Throwable t) {
                Log.e("Xflymetar: (TAF) HTTP RESPONSE ERROR", t.getMessage());
                PojoTaf data = new PojoTaf();
                data.setApiError(true);
                setTafData(state, data);
                // TODO implement proper error handling
            }
        });
    }

    /**
     * Simply the time interval with which this specific API updates its data feed.
     * In this case it's 10 minute intervals.
     *
     * @return api refresh time in milliseconds
     */
    @Override
    public long getAPIDataUpdateTime() {
        return 660000;
    }


    /* OBSERVER METHODS */

    /**
     * This is the method all classes that want to subscribe to the data must use to add
     * an instance of themselves to. This method should NEVER be called directly - only through the
     * APIDatASingleton instance. See MetarView.class in the fragment folder for an example.
     *
     * @param dataio the class wanting to add itself must implement DataObserverIO and add itself
     * as parameter here.
     * @see DataObserverIO
     * @see APIDataSingleton
     * @see com.mydogspies.xflymetar.fragments.MetarView
     *
     */
    public void addObserver(DataObserverIO dataio) {
        this.observers.add(dataio);
    }

    public void removeObserver(DataObserverIO dataio) {
        this.observers.remove(dataio);
    }

    /**
     * Parses and feeds the fresh METAR data object to the observers
     * @param data the incoming object with API metar data
     * @param state
     */
    public void setMetarData(VIEWSTATE state, PojoMetar data) {
        Metar metar = dataParser.parseMetarPojo(state, data);
        timestampMap.replace(state, new Date());

        for (DataObserverIO dataio : this.observers) {
            dataio.updateMetarFromAPI(state, metar);
        }
    }

    /**
     * Parses and feeds the fresh TAF data object to the observers
     * @param data the incoming object with API metar data
     * @param state
     */
    public void setTafData(VIEWSTATE state, PojoTaf data) {
        Taf taf = dataParser.parseTafPojo(state, data);
        timestampMap.replace(state, new Date());

        for (DataObserverIO dataio : this.observers) {
            dataio.updateTafFromAPI(state, taf);
        }
    }

    /**
     * Looks up the saved METAR object and pushes it to the observer network.
     * @param state
     */
    public void getSavedMetarData(VIEWSTATE state) {
        Metar currentData = (Metar) currentAPIData.get(state);
        for (DataObserverIO dataio : this.observers) {
            dataio.updateMetarFromAPI(state, currentData);
        }
    }

    /**
     * Looks up the saved TAF object and pushes it to the observer network.
     * @param state
     */
    public void getSavedTafData(VIEWSTATE state) {
        Taf currentData = (Taf) currentAPIData.get(state);
        for (DataObserverIO dataio : this.observers) {
            dataio.updateTafFromAPI(state, currentData);
        }
    }
}

