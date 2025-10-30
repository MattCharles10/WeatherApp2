🌤️ Weather App
A modern Android weather application built with Kotlin and Jetpack Compose that displays real-time weather data and 5-day forecasts. Features a clean Material Design 3 interface with temperature unit toggle, built using MVVM architecture with Retrofit for API integration.

📱 Features
Current Weather - Real-time weather conditions for any city

5-Day Forecast - Detailed weather predictions

Temperature Unit Toggle - Switch between Celsius and Fahrenheit

Modern UI - Jetpack Compose with Material Design 3

MVVM Architecture - Clean architecture pattern

Error Handling - Graceful error management

Loading States - Smooth user experience

🛠️ Tech Stack
Kotlin - Programming language

Jetpack Compose - Modern declarative UI

MVVM Architecture - Model-View-ViewModel pattern

Retrofit - HTTP client for API calls

WeatherAPI - Weather data provider

Material Design 3 - Design system

📸 Screenshots
(Add screenshots of your app here)

🚀 Installation
Clone the repository

bash
git clone https://github.com/yourusername/weatherapp.git
Open in Android Studio

Open Android Studio

Select "Open an existing project"

Navigate to the cloned directory

Get API Key

Sign up at WeatherAPI.com

Get your free API key

Replace YOUR_API_KEY in WeatherRepository.kt

Build and Run

Connect Android device or emulator

Build and run the application

📁 Project Structure
text
app/src/main/java/com/example/weatherapp/
├── data/
│   ├── api/           # API service and Retrofit setup
│   ├── model/         # Data models
│   └── repository/    # Data layer
├── ui/
│   ├── viewmodel/     # ViewModels
│   ├── state/         # UI state management
│   └── WeatherApp.kt  # Main composable
└── MainActivity.kt    # App entry point
🔧 Configuration
Add your WeatherAPI key in WeatherRepository.kt:

kotlin
apiKey = "your_actual_api_key_here"
📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Developer
Your Name
Vel Tech University
Submitted to: trainer19@veltech.edu.in

Submission Date: October 31, 2025
Course: Android Development
University: Vel Tech University


