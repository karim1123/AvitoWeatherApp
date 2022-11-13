package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing weather forecast for hour.
 */
data class WeatherForHourModel(
    val hour: Int,
    val temperature: String,
    val feelsLikeTemperature: String,
    val weatherIcon: String,
    val weatherCondition: String,
    val windSpeed: Double,
    val windDirection: String,
    val pressureInMM: Int,
    val humidity: Int,
    var date: String = ""
)
