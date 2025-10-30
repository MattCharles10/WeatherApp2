package com.example.weatherapp.ui.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.weatherapp.Data.repository.WeatherRepository
import com.example.weatherapp.ui.state.WeatherUiState

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: StateFlow<Boolean> = _isCelsius.asStateFlow()

    fun toggleTemperatureUnit() {
        _isCelsius.value = !_isCelsius.value
    }

    fun searchWeather(city: String) {
        if (city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            val weatherResult = repository.getWeatherData(city)

            if (weatherResult.isSuccess) {
                _uiState.value = WeatherUiState.Success(
                    weatherData = weatherResult.getOrNull()!!
                )
            } else {
                val error = weatherResult.exceptionOrNull()
                _uiState.value = WeatherUiState.Error(error?.message ?: "Unknown error")
            }
        }
    }
}