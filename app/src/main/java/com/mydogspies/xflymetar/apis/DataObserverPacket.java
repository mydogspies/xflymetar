package com.mydogspies.xflymetar.apis;

public class DataObserverPacket {

    private GetXMLData.Pojo data;

    public DataObserverPacket(GetXMLData.Pojo data) {
        this.data = data;
    }

    public GetXMLData.Pojo getData() {
        return data;
    }
}
