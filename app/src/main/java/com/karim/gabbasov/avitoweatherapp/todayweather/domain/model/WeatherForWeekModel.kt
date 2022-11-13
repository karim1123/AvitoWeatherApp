package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing weather forecast for week.
 */
data class WeatherForWeekModel(
    val morningWeather: List<WeatherForDayPartModel>,
    val dayWeather: List<WeatherForDayPartModel>,
    val eveningWeather: List<WeatherForDayPartModel>,
    val nightWeather: List<WeatherForDayPartModel>
)
