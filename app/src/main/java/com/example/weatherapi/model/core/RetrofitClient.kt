package com.example.rest_api_weather.model.core

import com.example.rest_api_weather.model.models.WeatherRespons
import com.example.rest_api_weather.model.service.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitClient {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY

    }


    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()



    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    val retrofitService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApiService::class.java)
    }
}
