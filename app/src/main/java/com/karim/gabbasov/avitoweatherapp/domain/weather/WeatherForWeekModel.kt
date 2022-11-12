package com.karim.gabbasov.avitoweatherapp.domain.weather

/**
 * Model containing weather forecast for week.
 */
data class WeatherForWeekModel(
    val morningWeather: List<WeatherForDayPartModel>,
    val dayWeather: List<WeatherForDayPartModel>,
    val eveningWeather: List<WeatherForDayPartModel>,
    val nightWeather: List<WeatherForDayPartModel>
)
