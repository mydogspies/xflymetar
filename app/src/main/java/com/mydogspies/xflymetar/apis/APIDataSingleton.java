package com.mydogspies.xflymetar.apis;

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
