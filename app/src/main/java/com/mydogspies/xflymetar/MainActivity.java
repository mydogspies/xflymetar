package com.mydogspies.xflymetar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mydogspies.xflymetar.network.APIConnection;
import com.mydogspies.xflymetar.network.AWConnect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        APIConnection con = new AWConnect();
        con.getMetarAsXMLString();
    }


}