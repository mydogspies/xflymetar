package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mydogspies.xflymetar.apis.APIDataIO;
import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.GetXMLData;
import com.mydogspies.xflymetar.apis.GetXMLDataSingleton;
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
        GetXMLData handler = new GetXMLData();
        GetXMLDataSingleton singleton = GetXMLDataSingleton.getInstance();
        singleton.setHandler(handler);
        handler.addObserver(this);

        ((APIDataIO) handler).getMetarAsObject("EDDT");
    }

    /* Observer methods for incoming data from tghe APIs */

    @Override
    public void updateMetarFromAPI(PojoMetar data) {
        System.out.println("data.getDataType() = " + data.getDataType());
    }

    @Override
    public void updateTafFromAPI(PojoTaf data) {

    }

    @Override
    public void updateAirportFromAPI(PojoAirport data) {

    }
}