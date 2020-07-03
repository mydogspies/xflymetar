package com.mydogspies.xflymetar.apis;

public class GetAPIDataSingleton {

    private static GetAPIDataSingleton instance;
    private GetAPIData handler;

    private GetAPIDataSingleton() {}

    public static GetAPIDataSingleton getInstance() {
        if (instance == null) {
            synchronized (GetAPIDataSingleton.class) {
                if (instance == null) {
                    instance = new GetAPIDataSingleton();
                }
            }
        }
        return instance;
    }

    public GetAPIData getHandler() { return handler;}

    public void setHandler(GetAPIData handler) { this.handler = handler;}
}
