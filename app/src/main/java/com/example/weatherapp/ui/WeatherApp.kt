package com.example.weatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.Data.api.RetrofitInstance
import com.example.weatherapp.Data.repository.WeatherRepository
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.viewmodel.WeatherViewModelFactory
import com.example.weatherapp.ui.state.WeatherUiState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherApp() {
    val viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            WeatherRepository(RetrofitInstance.weatherApiService)
        )
    )
    val uiState by viewModel.uiState.collectAsState()
    val isCelsius by viewModel.isCelsius.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        var city by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city name") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (city.isNotBlank()) viewModel.searchWeather(city)
            }) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature Toggle
        Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "°C",
                color = if (isCelsius) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            Switch(
                checked = !isCelsius,
                onCheckedChange = { viewModel.toggleTemperatureUnit() }
            )

            Text(
                "°F",
                color = if (!isCelsius) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on state
        when (val state = uiState) {
            is WeatherUiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Enter a city name to get weather information")
                }
            }
            is WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> {
                WeatherContent(
                    weatherData = state.weatherData,
                    isCelsius = isCelsius
                )
            }
            is WeatherUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherContent(
    weatherData: com.example.weatherapp.Data.Model.WeatherApiResponse,
    isCelsius: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Current Weather
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "${weatherData.location.name}, ${weatherData.location.country}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                val temperature = if (isCelsius) weatherData.current.temp_c else weatherData.current.temp_f
                val unit = if (isCelsius) "°C" else "°F"

                Text(
                    text = "${temperature.toInt()}$unit",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weatherData.current.condition.text,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    WeatherDetail("Humidity", "${weatherData.current.humidity}%")
                    Spacer(modifier = Modifier.width(16.dp))
                    val feelsLike = if (isCelsius) weatherData.current.feelslike_c else weatherData.current.feelslike_f
                    WeatherDetail("Feels like", "${feelsLike.toInt()}$unit")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Forecast
        Text(
            text = "5-Day Forecast",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        ForecastSection(forecast = weatherData.forecast, isCelsius = isCelsius)
    }
}

@Composable
fun WeatherDetail(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ForecastSection(
    forecast: com.example.weatherapp.Data.Model.Forecast,
    isCelsius: Boolean
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(forecast.forecastday) { forecastDay ->
            ForecastItem(forecastDay = forecastDay, isCelsius = isCelsius)
        }
    }
}

@Composable
fun ForecastItem(
    forecastDay: com.example.weatherapp.Data.Model.ForecastDay,
    isCelsius: Boolean
) {
    Card(
        modifier = Modifier.width(120.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Format date to show like "Mon 15"
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(forecastDay.date)
            val outputFormat = SimpleDateFormat("EEE dd", Locale.getDefault())
            val formattedDate = outputFormat.format(date ?: Date())

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            val maxTemp = if (isCelsius) forecastDay.day.maxtemp_c else forecastDay.day.maxtemp_f
            val minTemp = if (isCelsius) forecastDay.day.mintemp_c else forecastDay.day.mintemp_f

            Text(
                text = "${maxTemp.toInt()}${if (isCelsius) "°C" else "°F"}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${minTemp.toInt()}${if (isCelsius) "°C" else "°F"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = forecastDay.day.condition.text,
                style = MaterialTheme.typography.bodySmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}