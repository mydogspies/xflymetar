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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.GetAPIData;
import com.mydogspies.xflymetar.apis.GetAPIDataSingleton;
import com.mydogspies.xflymetar.apis.PojoAirport;
import com.mydogspies.xflymetar.apis.PojoMetar;
import com.mydogspies.xflymetar.apis.PojoTaf;

public class MainActivity extends AppCompatActivity implements DataObserverIO {

    private final String BACKGROUND_COLOR = "#374140";
    private final String INPUTBACKGR_COLOR = "#3A4543";
    private final String TEXT_COLOR = "#FEFEFE";

    ConstraintLayout mainLayout;
    EditText departAirportInputText;
    TextView departAirportHeaderText;
    EditText arrivalAirportInputText;
    TextView arrivalAirportHeaderText;

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
        GetAPIData handler = new GetAPIData();
        GetAPIDataSingleton singleton = GetAPIDataSingleton.getInstance();
        singleton.setHandler(handler);
        handler.addObserver(this);

        initBackground();
        initAirportInputs();

        // TODO dev only
        ((APIDataIO) handler).getMetarAsObject("EDDT");
        ((APIDataIO) handler).getTafAsObject("EDDT");
        ((APIDataIO) handler).getAirportAsObject("EDDT");
    }

    private void loadMetar(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.metarViewContainer, fragment);
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
        System.out.println("data errorFlag = " + data.isApiError());
        System.out.println("data AIRPORT = " + data.getName());
    }

    /* INIT METHODS */

    private void initBackground() {
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
    }

    private void initAirportInputs() {
        departAirportInputText = findViewById(R.id.departAirportInputText);
        departAirportInputText.setBackgroundColor(Color.parseColor(INPUTBACKGR_COLOR));
        departAirportInputText.setTextColor(Color.parseColor(TEXT_COLOR));
        departAirportHeaderText = findViewById(R.id.departAirportHeaderText);
        departAirportHeaderText.setTextColor(Color.parseColor(TEXT_COLOR));

        departAirportInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {
                    loadMetar(new MetarView(ViewState.DEPARTURE));
                }
            return false;
            }
        });

        arrivalAirportInputText = findViewById(R.id.arrivalAirportInputText);
        arrivalAirportInputText.setBackgroundColor(Color.parseColor(INPUTBACKGR_COLOR));
        arrivalAirportInputText.setTextColor(Color.parseColor(TEXT_COLOR));
        arrivalAirportHeaderText = findViewById(R.id.arrivalAirportHeaderText);
        arrivalAirportHeaderText.setTextColor(Color.parseColor(TEXT_COLOR));

        arrivalAirportInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {
                    loadMetar(new MetarView(ViewState.ARRIVAL));
                    loadMetar(new MetarView(ViewState.TAF));
                }
                return false;
            }
        });
    }


}