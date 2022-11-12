package com.karim.gabbasov.avitoweatherapp.domain.weather

/**
 * Model containing weather forecast for part of the day.
 */
data class WeatherForDayPartModel(
    val maxTemperature: String,
    val minTemperature: String,
    val avgTemperature: String,
    val feelsLikeTemperature: String,
    val weatherIcon: String,
    val weatherCondition: String,
    val windSpeed: Double,
    val windDirection: String,
    val pressureInMM: Int,
    val humidity: Int,
    val date: String,
    var dayOfWeek: String,
    var index: Int? = null
)
