package com.example.weatherapp.Data.api



import com.example.weatherapp.Data.Model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getWeatherAndForecast(
        @Query("q") city: String,
        @Query("days") days: Int = 5,
        @Query("key") apiKey: String
    ): WeatherApiResponse
}