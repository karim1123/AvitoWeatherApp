package com.karim.gabbasov.avitoweatherapp.todayweather.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents forecasts for day and week.
 */
data class ForecastsWeatherDto(
    @field:Json(name = "hours")
    val hourlyWeatherData: List<WeatherDataDto>,
    @field:Json(name = "date_ts")
    val unixTime: Long,
    @field:Json(name = "parts")
    val weatherDataForPartsOfDay: WeatherByPartsOfDayDto
)
