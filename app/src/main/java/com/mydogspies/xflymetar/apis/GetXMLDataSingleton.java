package com.mydogspies.xflymetar.apis;

public class GetXMLDataSingleton {

    private static GetXMLDataSingleton instance;
    private GetXMLData handler;

    private GetXMLDataSingleton() {}

    public static GetXMLDataSingleton getInstance() {
        if (instance == null) {
            synchronized (GetXMLDataSingleton.class) {
                if (instance == null) {
                    instance = new GetXMLDataSingleton();
                }
            }
        }
        return instance;
    }

    public GetXMLData getHandler() { return handler;}

    public void setHandler(GetXMLData handler) { this.handler = handler;}
}
