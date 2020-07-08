package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.GetAPIData;
import com.mydogspies.xflymetar.apis.GetAPIDataSingleton;
import com.mydogspies.xflymetar.apis.PojoAirport;
import com.mydogspies.xflymetar.apis.PojoMetar;
import com.mydogspies.xflymetar.apis.PojoTaf;
import com.mydogspies.xflymetar.parser.Validators;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DataObserverIO {

    private ViewStyles STYLES = new ViewStyles();
    private Validators validator = new Validators();
    public static Map<ViewState, Object> dataViewObjects;

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

        GetAPIData handler = new GetAPIData();
        GetAPIDataSingleton singleton = GetAPIDataSingleton.getInstance();
        singleton.setHandler(handler);
        handler.addObserver(this);

        initBackground();
        initAirportInputs();

        // TODO dev only
        ((APIDataIO) handler).getMetarAsObject("EDDT");
        ((APIDataIO) handler).getTafAsObject("EDDT");
        // ((APIDataIO) handler).getAirportAsObject("EDDT");
    }


    /* METHODS FOR LOADING FRAGMENTS */

    private void loadDataViews(Fragment fragment, String icaoCode) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.dataViewContainer, fragment);
        fragmentTransaction.commit();
    }

    private void loadAirportView(Fragment fragment, String icaoCode) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.airportDataContainer, fragment);
        fragmentTransaction.commit();
    }



    /* ACTION EVENTS */

    // TODO dev only for develop-api branch
    /* Observer methods for incoming data from the APIs */

    @Override
    public void updateMetarFromAPI(PojoMetar data) {
        System.out.println("data errorFlag = " + data.isApiError());
        System.out.println("data METAR = " + data.getRaw_text());
    }

    @Override
    public void updateTafFromAPI(PojoTaf data) {
        System.out.println("data errorFlag = " + data.isApiError());
        System.out.println("data TAF = " + data.getRaw_text());
    }

    @Override
    public void updateAirportFromAPI(PojoAirport data) {
        // TODO this will become a static database instead of this api
        //System.out.println("data errorFlag = " + data.isApiError());
        //System.out.println("data AIRPORT = " + data.getName());
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

        // Listener logic for the ICAO code input field
        // Calls the methods for attaching the various fragments with data views
        departAirportInputText.setOnKeyListener((view, i, keyEvent) -> {
            String airportCode = departAirportInputText.getText().toString();

            if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {

                if (validator.validateIcaoCode(airportCode)) {
                    loadDataViews(new DataViews(ViewState.DEPARTURE), airportCode);
                    MetarView metarView = new MetarView();
                    loadDataViews(metarView, airportCode);
                    dataViewObjects.put(ViewState.DEPARTURE, metarView);

                    AirportView airportView = new AirportView();
                    loadAirportView(airportView, airportCode);
                    dataViewObjects.put(ViewState.DEPARTURE_AIRPORT, airportView);
                    return true;
                }
            }
            return false;
        });

        // Listener and visibility logic for the airport info icon
        departureAirportIcon = findViewById(R.id.departureAirportInfoIcon);
        departureAirportIcon.setOnClickListener(view -> {
            setDepartureAirportVisiblity();
        });


        /* ARRIVAL AIRPORT INPUTS */

        // Listener and visibility logic for the airport info icon
        arrivalAirportIcon = findViewById(R.id.arrivalAirportInfoIcon);
        arrivalAirportIcon.setOnClickListener(view -> {
            setarrivalAirportVisiblity();
        });

        arrivalAirportInputText = findViewById(R.id.arrivalAirportInputText);
        arrivalAirportInputText.setBackgroundColor(Color.parseColor(STYLES.INPUT_BACKGROUND_COLOR));
        arrivalAirportInputText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));
        arrivalAirportHeaderText = findViewById(R.id.arrivalAirportHeaderText);
        arrivalAirportHeaderText.setTextColor(Color.parseColor(STYLES.TEXT_COLOR));

        // Listener logic for the ICAO code input field
        // Calls the methods for attaching the various fragments with data views
        arrivalAirportInputText.setOnKeyListener((view, i, keyEvent) -> {
            String airportCode = departAirportInputText.getText().toString();

            if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {

                if (validator.validateIcaoCode(airportCode)) {
                    loadDataViews(new DataViews(ViewState.ARRIVAL), airportCode);
                    MetarView metarView = new MetarView();
                    loadDataViews(metarView, airportCode);
                    dataViewObjects.put(ViewState.ARRIVAL, metarView);

                    AirportView airportView = new AirportView();
                    loadAirportView(airportView, airportCode);
                    dataViewObjects.put(ViewState.ARRIVAL_AIRPORT, airportView);

                    loadDataViews(new DataViews(ViewState.TAF), airportCode);
                    TafView tafView = new TafView();
                    loadDataViews(tafView, airportCode);
                    dataViewObjects.put(ViewState.TAF, tafView);
                    return true;
                }
            }
            return false;
        });
    }

    /* VISIBILITY OF AIRPORT INFO FRAGMENTS */

    public void setDepartureAirportVisiblity() {
        AirportView airportView = (AirportView) dataViewObjects.get(ViewState.DEPARTURE_AIRPORT);

        if(departureAirportVisibility && airportView != null) {
            airportView.setVisiblity(false);
            departureAirportVisibility = false;
        } else if (airportView != null) {
            airportView.setVisiblity(true);
            departureAirportVisibility = true;
        }
    }

    public void setarrivalAirportVisiblity() {
        AirportView airportView = (AirportView) dataViewObjects.get(ViewState.ARRIVAL_AIRPORT);

        if(arrivalAirportVisibility && airportView != null) {
            airportView.setVisiblity(false);
            arrivalAirportVisibility = false;
        } else if (airportView != null) {
            airportView.setVisiblity(true);
            arrivalAirportVisibility = true;
        }
    }
}