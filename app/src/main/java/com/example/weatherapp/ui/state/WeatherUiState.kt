package com.example.weatherapp.ui.state

import com.example.weatherapp.Data.Model.ForecastResponse
import com.example.weatherapp.Data.Model.WeatherResponse



import com.example.weatherapp.Data.Model.WeatherApiResponse

sealed class WeatherUiState {
    object Empty : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val weatherData: WeatherApiResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}