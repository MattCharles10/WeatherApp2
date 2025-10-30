package com.example.weatherapp.Data.repository






import com.example.weatherapp.Data.api.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val apiService: WeatherApiService) {

    suspend fun getWeatherData(city: String): Result<com.example.weatherapp.Data.Model.WeatherApiResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getWeatherAndForecast(
                    city = city,
                    apiKey = "f3d0e312e5cd4ee6b1b53834253010" // ← Replace with actual key
                )
                Result.success(response)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}