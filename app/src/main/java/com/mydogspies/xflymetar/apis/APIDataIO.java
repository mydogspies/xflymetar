package com.mydogspies.xflymetar.apis;

import com.mydogspies.xflymetar.VIEWSTATE;

public interface APIDataIO {

        void loadMetarFromAPI(VIEWSTATE state, String airport);
        void loadTafFromAPI(VIEWSTATE state, String airport);

        // The refresh time interval in milliseconds for the API
        long getAPIDataUpdateTime();
}
