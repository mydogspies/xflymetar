package com.mydogspies.xflymetar.apis;

import com.mydogspies.xflymetar.VIEWSTATE;

/**
 * This interface is the endpoint for the API logic and should be implemented by the API
 * handler.
 * @author github.com/mydogspies
 * @since 0.1.0
 * @see APIData
 */
public interface APIDataIO {

        void loadMetarFromAPI(VIEWSTATE state, String airport);
        void loadTafFromAPI(VIEWSTATE state, String airport);

        // The refresh time interval in milliseconds for the API
        long getAPIDataUpdateTime();
}
