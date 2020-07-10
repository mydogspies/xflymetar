package com.mydogspies.xflymetar.apis;

/**
 * This class is the single only reference to the API data handler.
 * It is an integral part of the observer network. Every class that needs to be added as an
 * observer should use this Singleton as reference to the APIData handler class and its
 * addObserver method.
 * @author github.com/mydogspies
 * @since 0.1.0
 * @see APIData
 * @see APIDataIO
 * @see DataObserverIO
 */
public class APIDataSingleton {

    private static APIDataSingleton instance;
    private APIData handler;

    private APIDataSingleton() {}

    public static APIDataSingleton getInstance() {
        if (instance == null) {
            synchronized (APIDataSingleton.class) {
                if (instance == null) {
                    instance = new APIDataSingleton();
                }
            }
        }
        return instance;
    }

    public APIData getHandler() { return handler;}

    public void setHandler(APIData handler) { this.handler = handler;}
}
