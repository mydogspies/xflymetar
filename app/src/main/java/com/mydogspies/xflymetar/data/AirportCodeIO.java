package com.mydogspies.xflymetar.data;

/**
 * A tiny utility interface so that we can extract ICAO code from any of data objects without
 * having to know the object type. Used with fetchAPIData() method in the MainActivity together with
 * the currentAPIData map.
 * @author github.com/mydogspies
 * @since 0.1.0
 */
public interface AirportCodeIO {

    public String getIcaoCode();
}
