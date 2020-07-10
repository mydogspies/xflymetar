package com.mydogspies.xflymetar.apis;

import com.mydogspies.xflymetar.VIEWSTATE;
import com.mydogspies.xflymetar.data.Metar;
import com.mydogspies.xflymetar.data.Taf;

/**
 * This interface is an integral part of the observer network.
 * The methods below should be implemented in each class that needs to have data
 * pushed to it in real time.
 * @author github.com/mydogspies
 * @since 0.1.0
 * @see DataObserverIO
 * @see APIDataIO
 * @see APIDataSingleton
 */
public interface DataObserverIO {

    void updateMetarFromAPI(VIEWSTATE state, Metar metar);
    void updateTafFromAPI(VIEWSTATE state, Taf taf);
}
