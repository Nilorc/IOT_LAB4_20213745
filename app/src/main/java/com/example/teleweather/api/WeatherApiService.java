package com.example.teleweather.api;

import com.example.teleweather.fragments.ForecastModel;
import com.example.teleweather.fragments.FutureForecastModel;
import com.example.teleweather.fragments.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("search.json")
    Call<List<LocationModel>> buscarLocaciones(
            @Query("key") String apiKey,
            @Query("q") String query
    );

    @GET("forecast.json")
    Call<ForecastModel> obtenerPronostico(
            @Query("key") String key,
            @Query("q") String lugar,
            @Query("days") int dias
    );
    @GET("future.json")
    Call<FutureForecastModel> obtenerPronosticoFuturo(
            @Query("key") String apiKey,
            @Query(value = "q", encoded = true) String idLocation,
            @Query("dt") String fecha
    );


}
