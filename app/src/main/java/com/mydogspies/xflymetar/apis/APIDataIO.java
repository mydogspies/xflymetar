package com.mydogspies.xflymetar.apis;

public interface APIDataIO {

        void getMetarAsObject(String airport);
        void getTafAsObject(String airport);
        void getAirportAsObject(String airport);
}
