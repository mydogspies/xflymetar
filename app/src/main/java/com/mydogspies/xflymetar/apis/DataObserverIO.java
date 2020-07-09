package com.mydogspies.xflymetar.apis;

import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;

public interface DataObserverIO {

    void updateMetarFromAPI(VIEWSTATE state, Metar metar);
    void updateTafFromAPI(VIEWSTATE state, Taf taf);
}
