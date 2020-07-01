package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mydogspies.xflymetar.apis.DataObserverIO;
import com.mydogspies.xflymetar.apis.DataObserverPacket;
import com.mydogspies.xflymetar.apis.GetXMLData;
import com.mydogspies.xflymetar.apis.GetXMLDataSingleton;

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
        handler.getMetarAsObject(); // TODO this only for dev
    }

    // TODO only for dev
    @Override
    public void updateFromAPI(DataObserverPacket packet) {

        GetXMLData.Pojo data = packet.getData();
        for (GetXMLData.Food item : data.getList()) {
            System.out.println(item.getName());
        }
    }
}