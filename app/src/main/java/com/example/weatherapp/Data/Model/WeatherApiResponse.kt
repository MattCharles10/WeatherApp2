package com.example.weatherapp.Data.Model


data class WeatherApiResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val country: String
)

data class Current(
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val humidity: Int,
    val feelslike_c: Double,
    val feelslike_f: Double
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day
)

data class Day(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)