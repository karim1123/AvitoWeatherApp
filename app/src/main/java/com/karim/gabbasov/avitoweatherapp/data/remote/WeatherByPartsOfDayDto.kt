package com.karim.gabbasov.avitoweatherapp.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents all weather forecast for week.
 */
data class WeatherByPartsOfDayDto(
    @field:Json(name = "morning")
    val morningWeatherData: WeatherDataDto,
    @field:Json(name = "day")
    val dayWeatherData: WeatherDataDto,
    @field:Json(name = "evening")
    val eveningWeatherData: WeatherDataDto,
    @field:Json(name = "night")
    val nightWeatherData: WeatherDataDto
)
