package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.APIData;
import com.mydogspies.xflymetar.apis.APIDataSingleton;
import com.mydogspies.xflymetar.data.AirportCodeIO;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;
import com.mydogspies.xflymetar.fragments.ArrivalMetarHeader;
import com.mydogspies.xflymetar.fragments.ArrivalTafHeader;
import com.mydogspies.xflymetar.fragments.DepartureMetarHeader;
import com.mydogspies.xflymetar.parser.Validators;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DataObserverIO {

    // TODO - IMPORTANT! Implement periodic API data update

    private ViewStyles STYLES = new ViewStyles();
    private Validators validator = new Validators();
    private APIData handler;

    // All the current view fragments - populated by initAirportInputs() by fragment loading.
    // This global is the used to have a direct reference to each fragment, for example for the
    // visibility logic.
    public static Map<VIEWSTATE, Object> dataViewObjects;

    // This is the global saved api time stamp map upon which the logic relies on when checking
    // if to make an API call or use current saved data
    public static Map<VIEWSTATE, Date> apiTimeStamps;

    public static Map<VIEWSTATE, Object> currentAPIData;

    ConstraintLayout mainLayout;
    EditText departAirportInputText;
    TextView departAirportHeaderText;
    EditText arrivalAirportInputText;
    TextView arrivalAirportHeaderText;
    ImageView departureAirportIcon;
    ImageView arrivalAirportIcon;
    boolean departureAirportVisibility = false;
    boolean arrivalAirportVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        dataViewObjects = new HashMap<>();
        initDataTimestamps();
        initCurrentAPIData();

        // set a new instance for the API data handler
        handler = new APIData();
        APIDataSingleton singleton = APIDataSingleton.getInstance();
        singleton.setHandler(handler);
        handler.addObserver(this); // TODO only for dev purposes

        initBackground();
        initAirportInputs();
    }

    /**
     * Using a date in the past this method initializes the time stamp map in order to
     * trigger the API logic to update the data from the very start without the need for
     * a separate init method.
     */
    private void initDataTimestamps() {
        apiTimeStamps = new HashMap<>();
        Date date = new Date(1);
        apiTimeStamps.put(VIEWSTATE.DEPARTURE_METAR, date);
        apiTimeStamps.put(VIEWSTATE.DEPARTURE_TAF, date);
        apiTimeStamps.put(VIEWSTATE.ARRIVAL_METAR, date);
        apiTimeStamps.put(VIEWSTATE.ARRIVAL_TAF, date);
    }

    private void initCurrentAPIData() {
        currentAPIData = new HashMap<>();
        Metar metardummy = new Metar(VIEWSTATE.DEPARTURE_METAR, "", new Date(0));
        Taf tafDummy = new Taf(VIEWSTATE.DEPARTURE_TAF, "", new Date(0));
        currentAPIData.put(VIEWSTATE.DEPARTURE_METAR, metardummy);
        currentAPIData.put(VIEWSTATE.DEPARTURE_TAF, tafDummy);
        currentAPIData.put(VIEWSTATE.ARRIVAL_METAR, metardummy);
        currentAPIData.put(VIEWSTATE.ARRIVAL_TAF, tafDummy);
    }



    /* METHODS FOR LOADING FRAGMENTS */

    private void loadDataViews(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.dataViewContainer, fragment);
        fragmentTransaction.commit();
    }

    private void loadAirportView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.airportDataContainer, fragment);
        fragmentTransaction.commit();
    }



    /* ACTION EVENTS */

    // TODO dev only for develop-api branch
    /* Observer methods for incoming data from the APIs */

    @Override
    public void updateMetarFromAPI(VIEWSTATE state, Metar data) {
    }

    @Override
    public void updateTafFromAPI(VIEWSTATE state, Taf data) {
    }

    /* INIT METHODS */

    private void initBackground() {
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(Color.parseColor(STYLES.BACKGROUND_COLOR));
    }

    /**
     * Initializes all ICAO code inputs and corresponding buttons.
     * Calls the methods to load the individual fragments with various data views, stores
     * the view objects into a the global HashMap and sets the visibility logic.
     */
    private void initAirportInputs() {

        /* DEPARTURE AIRPORT INPUTS */

        departAirportInputText = findViewById(R.id.departAirportInputText);
        departAirportInputText.setBackgroundColor(Color.parseColor(STYLES.INPUT_BACKGROUND_COLOR));
        departAirportInputText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        departAirportHeaderText = findViewById(R.id.departAirportHeaderText);
        departAirportHeaderText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));

        // metar data view
        loadDataViews(new DepartureMetarHeader(VIEWSTATE.DEPARTURE_METAR));
        MetarView departureMetarView = new MetarView();
        loadDataViews(departureMetarView);
        dataViewObjects.put(VIEWSTATE.DEPARTURE_METAR, departureMetarView);

        // airport info view
        AirportView departureAirportView = new AirportView();
        loadAirportView(departureAirportView);
        dataViewObjects.put(VIEWSTATE.DEPARTURE_AIRPORT_INFO, departureAirportView);

        departAirportInputText.setOnKeyListener((view, i, keyEvent) -> {
            String airportCode = departAirportInputText.getText().toString();

            if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {

                if (validator.validateIcaoCode(airportCode)) {
                    fetchAPIData(VIEWSTATE.DEPARTURE_METAR, airportCode);

                    // Listener and visibility logic for the airport info icon
                    departureAirportIcon = findViewById(R.id.departureAirportInfoIcon);
                    departureAirportIcon.setOnClickListener(v -> {
                        setDepartureAirportVisibility();
                    });
                    return true;
                }
            }
            return false;
        });


        /* ARRIVAL AIRPORT INPUTS */

        arrivalAirportInputText = findViewById(R.id.arrivalAirportInputText);
        arrivalAirportInputText.setBackgroundColor(Color.parseColor(STYLES.INPUT_BACKGROUND_COLOR));
        arrivalAirportInputText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        arrivalAirportHeaderText = findViewById(R.id.arrivalAirportHeaderText);
        arrivalAirportHeaderText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));

        // metar data view
        loadDataViews(new ArrivalMetarHeader(VIEWSTATE.ARRIVAL_METAR));
        MetarView arrivalMetarView = new MetarView();
        loadDataViews(arrivalMetarView);
        dataViewObjects.put(VIEWSTATE.ARRIVAL_METAR, arrivalMetarView);

        // airport info view
        AirportView arrivalAirportView = new AirportView();
        loadAirportView(arrivalAirportView);
        dataViewObjects.put(VIEWSTATE.ARRIVAL_AIRPORT_INFO, arrivalAirportView);

        // taf data view
        loadDataViews(new ArrivalTafHeader(VIEWSTATE.ARRIVAL_TAF));
        TafView arrivalTafView = new TafView();
        loadDataViews(arrivalTafView);
        dataViewObjects.put(VIEWSTATE.ARRIVAL_TAF, arrivalTafView);

        // Listener logic for the ICAO code input field
        // Calls the methods for attaching the various fragments with data views
        arrivalAirportInputText.setOnKeyListener((view, i, keyEvent) -> {
            String airportCode = arrivalAirportInputText.getText().toString();

            if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {

                if (validator.validateIcaoCode(airportCode)) {
                    fetchAPIData(VIEWSTATE.ARRIVAL_TAF, airportCode);
                    fetchAPIData(VIEWSTATE.ARRIVAL_METAR, airportCode);

                    // Listener and visibility logic for the airport info icon
                    arrivalAirportIcon = findViewById(R.id.arrivalAirportInfoIcon);
                    arrivalAirportIcon.setOnClickListener(v -> {
                        setArrivalAirportVisibility();
                    });
                    return true;
                }
            }
            return false;
        });
    }

    private void fetchAPIData(VIEWSTATE state, String airportCode) {

        long refreshTime = handler.getAPIDataUpdateTime();
        long lastUpdatedTime = apiTimeStamps.get(state).getTime();
        long timeDiff = new Date().getTime() - lastUpdatedTime;

        AirportCodeIO storedCode = (AirportCodeIO) currentAPIData.get(state);
        String formattedCode = storedCode.getIcaoCode().toLowerCase();

        // TODO implement a system whereby the logic saves several past states to compare to - when for example going back and forth between same two airport codes

        if (timeDiff > refreshTime || !airportCode.toLowerCase().equals(formattedCode)) {
            if (state.equals(VIEWSTATE.DEPARTURE_METAR) || state.equals(VIEWSTATE.ARRIVAL_METAR)) {
                handler.loadMetarFromAPI(state, airportCode);
            } else {
                handler.loadTafFromAPI(state, airportCode);
            }

        } else {
            Log.i("Xflymetar: FetchAPIData()","Data up to date. Loading from current saved state.");
            if (state.equals(VIEWSTATE.DEPARTURE_METAR) || state.equals(VIEWSTATE.ARRIVAL_METAR)) {
                handler.getSavedMetarData(state);
            } else {
                handler.getSavedTafData(state);
            }
        }
    }

    /* VISIBILITY OF AIRPORT INFO FRAGMENTS */

    public void setDepartureAirportVisibility() {
        AirportView airportView = (AirportView) dataViewObjects.get(VIEWSTATE.DEPARTURE_AIRPORT_INFO);

        if(departureAirportVisibility && airportView != null) {
            airportView.setVisiblity(false);
            departureAirportVisibility = false;
        } else if (airportView != null) {
            airportView.setVisiblity(true);
            departureAirportVisibility = true;
        }
    }

    public void setArrivalAirportVisibility() {
        AirportView airportView = (AirportView) dataViewObjects.get(VIEWSTATE.ARRIVAL_AIRPORT_INFO);

        if(arrivalAirportVisibility && airportView != null) {
            airportView.setVisiblity(false);
            arrivalAirportVisibility = false;
        } else if (airportView != null) {
            airportView.setVisiblity(true);
            arrivalAirportVisibility = true;
        }
    }
}