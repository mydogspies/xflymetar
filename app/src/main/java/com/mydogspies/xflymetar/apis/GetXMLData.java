package com.mydogspies.xflymetar.apis;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class GetXMLData implements APIDataIO {

    private final List<DataObserverIO> observers = new ArrayList<>();

    interface APIconnect {
        // @GET("adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&hoursBeforeNow=3&stationString=eddt")
        @GET("https://www.w3schools.com/xml/simple.xml")
        Call<Pojo> getAWData();
    }

    @Override
    public void getMetarAsObject() {

        // TODO is there an alternative to SimpleXMLConverter?

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        // TODO we need HTTP response error handling

        APIconnect con = retro.create(APIconnect.class);
        Call<Pojo> call = con.getAWData();

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                setData(response.body());
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                // TODO implement proper error handling
                System.out.println("EXCEPTION: " + t.getMessage());
            }
        });
    }

    /* The observable methods */

    public void addObserver(DataObserverIO dataio) {
        this.observers.add(dataio);
    }

    public void removeObserver(DataObserverIO dataio) {
        this.observers.remove(dataio);
    }

    public void setData(Pojo data) {
        DataObserverPacket packet = new DataObserverPacket(data);
        for (DataObserverIO dataio : this.observers) {
            dataio.updateFromAPI(packet);
        }
    }

    /* RETROFIT XML STRUCTURE */

    @Root
    public static class Pojo {

        @ElementList(inline = true)
        private List<Food> list;

        public List<Food> getList() {
            return list;
        }
    }

    @Root(strict = false)
    public static class Food {

        @Element
        private String name;

        @Element
        private String price;

        @Element
        private String description;

        @Element
        private String calories;

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public String getCalories() {
            return calories;
        }
    }
}
