package com.karim.gabbasov.avitoweatherapp.weekforecast.domain

/**
 * Model containing weather forecast for day divided into parts of the day.
 */
sealed class WeatherForecastForDayByDayPartsModel {
    data class TemperatureParameter(
        val weatherIcons: List<String>,
        val temperatures: List<String>,
        val feelsLikeTemperatures: List<String>
    ) : WeatherForecastForDayByDayPartsModel()

    data class OtherWeatherParameters(
        val parameterName: String,
        val parameterIcon: Int,
        val parameters: List<String>
    ) : WeatherForecastForDayByDayPartsModel()
}
