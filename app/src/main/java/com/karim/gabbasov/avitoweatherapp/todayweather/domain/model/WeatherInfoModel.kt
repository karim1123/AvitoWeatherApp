package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing all weather forecast information.
 */
data class WeatherInfoModel(
    val currentWeatherForHourModel: WeatherForHourModel,
    val hourlyModelWeatherData: List<WeatherForHourModel>,
    val locationModel: LocationModel,
    val weatherForWeekModel: WeatherForWeekModel
)
