package com.karim.gabbasov.avitoweatherapp.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents all weather forecast information.
 */
data class WeatherDto(
    @field:Json(name = "fact")
    val currentWeatherData: WeatherDataDto,
    @field:Json(name = "forecasts")
    val forecastsWeatherData: List<ForecastsWeatherDto>,
    @field:Json(name = "now")
    val currentUnixTime: Long,
    @field:Json(name = "geo_object")
    val location: WeatherLocationDto
)
