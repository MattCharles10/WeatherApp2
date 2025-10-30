package com.example.weatherapp.Data.Model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val coord: Coord
)

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
)

data class City(
    val name: String,
    val country: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Int
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)