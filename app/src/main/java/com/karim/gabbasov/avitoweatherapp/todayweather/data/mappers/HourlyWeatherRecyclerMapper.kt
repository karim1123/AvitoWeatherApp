package com.karim.gabbasov.avitoweatherapp.todayweather.data.mappers

import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.HourlyWeatherRecyclerModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForHourModel

/**
 * Provides ability to map between object instances.
 */
object HourlyWeatherRecyclerMapper {
    /**
     * Converts an instance of List[WeatherForHourModel] into a List[HourlyWeatherRecyclerModel].
     */
    fun getTodayWeatherModel(hourlyModelWeatherData: List<WeatherForHourModel>): List<HourlyWeatherRecyclerModel> {
        val hourlyWeather = mutableListOf<HourlyWeatherRecyclerModel>()
        hourlyWeather.add(
            HourlyWeatherRecyclerModel.HourlyWeatherRecyclerDetails(
                windSpeed = hourlyModelWeatherData[0].windSpeed,
                windDirection = hourlyModelWeatherData[0].windDirection,
                pressureInMM = hourlyModelWeatherData[0].pressureInMM,
                humidity = hourlyModelWeatherData[0].humidity
            )
        )
        hourlyModelWeatherData.forEach {
            if (it.date.isNotBlank()) {
                hourlyWeather.add(
                    HourlyWeatherRecyclerModel.WeatherForHourWithTitleRecycler(
                        hour = it.hour,
                        temperature = it.temperature,
                        weatherIcon = it.weatherIcon,
                        date = it.date
                    )
                )
            } else {
                hourlyWeather.add(
                    HourlyWeatherRecyclerModel.WeatherForHourRecycler(
                        hour = it.hour,
                        temperature = it.temperature,
                        weatherIcon = it.weatherIcon
                    )
                )
            }
        }
        return hourlyWeather
    }
}
