package com.karim.gabbasov.avitoweatherapp.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents weather forecast for hour or day.
 */
data class WeatherDataDto(
    @field:Json(name = "hour")
    val hour: Int,
    @field:Json(name = "temp")
    val temperature: Int,
    @field:Json(name = "feels_like")
    val feelsLikeTemperature: Int,
    @field:Json(name = "icon")
    val weatherIcon: String,
    @field:Json(name = "condition")
    val weatherCondition: String,
    @field:Json(name = "wind_speed")
    val windSpeed: Double,
    @field:Json(name = "wind_dir")
    val windDirection: String,
    @field:Json(name = "pressure_mm")
    val pressureInMM: Int,
    @field:Json(name = "humidity")
    val humidity: Int,
    @field:Json(name = "temp_max")
    val maxTemperature: Int,
    @field:Json(name = "temp_min")
    val minTemperature: Int,
    @field:Json(name = "temp_avg")
    val avgTemperature: Int
)
