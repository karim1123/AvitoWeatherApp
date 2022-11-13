package com.karim.gabbasov.avitoweatherapp.weekforecast.util

import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForDayPartModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForWeekModel
import com.karim.gabbasov.avitoweatherapp.weekforecast.domain.WeatherForecastForDayByDayPartsModel

/**
 * Provides ability to map between object instances.
 */
object WeekForecastMapper {
    /**
     * Converts an instance of [WeatherForWeekModel] into a [WeatherForecastForDayByDayPartsModel].
     */
    fun WeatherForWeekModel.toWeatherForecastByDayParts(dayIndex: Int) =
        listOf(
            WeatherForecastForDayByDayPartsModel.TemperatureParameter(
                weatherIcons = listOf(
                    this.morningWeather[dayIndex].weatherIcon,
                    this.dayWeather[dayIndex].weatherIcon,
                    this.eveningWeather[dayIndex].weatherIcon,
                    this.nightWeather[dayIndex].weatherIcon
                ),
                temperatures = listOf(
                    this.morningWeather[dayIndex].avgTemperature,
                    this.dayWeather[dayIndex].avgTemperature,
                    this.eveningWeather[dayIndex].avgTemperature,
                    this.nightWeather[dayIndex].avgTemperature
                ),
                feelsLikeTemperatures = listOf(
                    this.morningWeather[dayIndex].feelsLikeTemperature,
                    this.dayWeather[dayIndex].feelsLikeTemperature,
                    this.eveningWeather[dayIndex].feelsLikeTemperature,
                    this.nightWeather[dayIndex].feelsLikeTemperature
                )
            ),
            WeatherForecastForDayByDayPartsModel.OtherWeatherParameters(
                parameterName = WeatherParametersNames.Wind.parameterName,
                parameterIcon = R.drawable.ic_windy_title,
                parameters = listOf(
                    this.morningWeather[dayIndex].toWindSpeedAndDir(),
                    this.dayWeather[dayIndex].toWindSpeedAndDir(),
                    this.eveningWeather[dayIndex].toWindSpeedAndDir(),
                    this.nightWeather[dayIndex].toWindSpeedAndDir()
                )

            ),
            WeatherForecastForDayByDayPartsModel.OtherWeatherParameters(
                parameterName = WeatherParametersNames.Humidity.parameterName,
                parameterIcon = R.drawable.ic_humidity_title,
                parameters = listOf(
                    this.morningWeather[dayIndex].toHumidity(),
                    this.dayWeather[dayIndex].toHumidity(),
                    this.eveningWeather[dayIndex].toHumidity(),
                    this.nightWeather[dayIndex].toHumidity()
                )

            ),
            WeatherForecastForDayByDayPartsModel.OtherWeatherParameters(
                parameterName = WeatherParametersNames.Pressure.parameterName,
                parameterIcon = R.drawable.ic_pressure_title,
                parameters = listOf(
                    this.morningWeather[dayIndex].toPressure(),
                    this.dayWeather[dayIndex].toPressure(),
                    this.eveningWeather[dayIndex].toPressure(),
                    this.nightWeather[dayIndex].toPressure()
                )

            )
        )

    private fun WeatherForDayPartModel.toWindSpeedAndDir(): String =
        "${this.windSpeed} m/s, ${this.windDirection.uppercase()}"

    private fun WeatherForDayPartModel.toHumidity(): String =
        "${this.humidity}%"

    private fun WeatherForDayPartModel.toPressure(): String =
        "${this.pressureInMM} mmHg"
}
