package com.mydogspies.xflymetar.network;

import com.mydogspies.xflymetar.MainActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class AWConnect implements APIConnection {

    interface APIconnect {
        // @GET("adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&hoursBeforeNow=3&stationString=eddt")
        @GET("https://www.w3schools.com/xml/simple.xml")
        Call<Pojo> getAWData();
    }

    @Override
    public String getMetarAsXMLString() {

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://www.aviationweather.gov/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        APIconnect con = retro.create(APIconnect.class);
        Call<Pojo> call = con.getAWData();

        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo pojo = response.body();
                for (Food item : pojo.getList()) {
                    System.out.println(item.name);
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                System.out.println("EXCEPTION t = " + t);
            }

        });

        return null;
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
    }
}
