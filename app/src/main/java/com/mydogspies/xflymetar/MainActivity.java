package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.GetAPIData;
import com.mydogspies.xflymetar.apis.GetAPIDataSingleton;
import com.mydogspies.xflymetar.apis.PojoAirport;
import com.mydogspies.xflymetar.apis.PojoMetar;
import com.mydogspies.xflymetar.apis.PojoTaf;

public class MainActivity extends AppCompatActivity implements DataObserverIO {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        GetAPIData handler = new GetAPIData();
        GetAPIDataSingleton singleton = GetAPIDataSingleton.getInstance();
        singleton.setHandler(handler);
        handler.addObserver(this);

        ((APIDataIO) handler).getMetarAsObject("EDDT");
        ((APIDataIO) handler).getTafAsObject("EDDT");
        ((APIDataIO) handler).getAirportAsObject("EDDT");
    }

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
}