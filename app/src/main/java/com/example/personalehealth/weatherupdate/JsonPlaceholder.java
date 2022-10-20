package com.example.personalehealth.weatherupdate;


import java.util.List;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;


public interface JsonPlaceholder {

    @GET("weather")
    Response  getWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}
