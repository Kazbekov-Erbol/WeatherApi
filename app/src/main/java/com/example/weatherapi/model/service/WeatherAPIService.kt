package com.example.rest_api_weather.model.service

import com.example.rest_api_weather.model.models.WeatherRespons
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")

    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,

        @Query("q") location: String
    ): WeatherRespons
}