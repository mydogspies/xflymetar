package com.mydogspies.xflymetar.network;

import com.mydogspies.xflymetar.data.Airport;
import com.mydogspies.xflymetar.data.AirportData;
import com.mydogspies.xflymetar.data.Metar;

import java.util.HashMap;

public interface APIConnection {

        String getMetarAsXMLString();
}
