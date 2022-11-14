package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing weather forecast model for hourly recycler.
 */
sealed class HourlyWeatherRecyclerModel {
    data class HourlyWeatherRecyclerDetails(
        val windSpeed: Double,
        val windDirection: String,
        val pressureInMM: Int,
        val humidity: Int
    ) : HourlyWeatherRecyclerModel()

    data class WeatherForHourWithTitleRecycler(
        val hour: Int,
        val temperature: String,
        val weatherIcon: String,
        var date: String
    ) : HourlyWeatherRecyclerModel()

    data class WeatherForHourRecycler(
        val hour: Int,
        val temperature: String,
        val weatherIcon: String
    ) : HourlyWeatherRecyclerModel()
}
