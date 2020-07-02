package com.mydogspies.xflymetar.apis;

public interface DataObserverIO {

    void updateMetarFromAPI(PojoMetar data);
    void updateTafFromAPI(PojoTaf data);
    void updateAirportFromAPI(PojoAirport data);
}
