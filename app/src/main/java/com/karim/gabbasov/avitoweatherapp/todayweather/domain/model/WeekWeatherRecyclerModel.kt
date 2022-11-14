package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing weather forecast model for week recycler.
 */
sealed class WeekWeatherRecyclerModel {
    data class TitleItem(
        val textColor: String,
        val date: String,
        val dayOfWeek: String,
        val maxTemperature: String,
        val minTemperature: String,
        val weatherIcon: String,
        val index: Int
    ) : WeekWeatherRecyclerModel()

    data class WeekendItem(
        val textColor: String,
        val date: String,
        val dayOfWeek: String,
        val maxTemperature: String,
        val minTemperature: String,
        val weatherIcon: String,
        val index: Int
    ) : WeekWeatherRecyclerModel()

    data class WeekdayItem(
        val date: String,
        val dayOfWeek: String,
        val maxTemperature: String,
        val minTemperature: String,
        val weatherIcon: String,
        var index: Int
    ) : WeekWeatherRecyclerModel()
}
