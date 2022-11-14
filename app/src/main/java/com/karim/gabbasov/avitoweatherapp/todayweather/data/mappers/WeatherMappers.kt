package com.karim.gabbasov.avitoweatherapp.todayweather.data.mappers

import com.karim.gabbasov.avitoweatherapp.todayweather.data.remote.ForecastsWeatherDto
import com.karim.gabbasov.avitoweatherapp.todayweather.data.remote.WeatherDataDto
import com.karim.gabbasov.avitoweatherapp.todayweather.data.remote.WeatherDto
import com.karim.gabbasov.avitoweatherapp.todayweather.data.remote.WeatherLocationDto
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.CalendarUtil.unixTimeToCalendar
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.CalendarUtil.unixTimeToHours
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.CalendarUtil.unixTimeToTomorrowDate
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.HOURS_IN_A_DAY
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.NamesOfDayOfWeek.Companion.toDayOfWeekName
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.NamesOfMonths.Companion.toMonthName
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.LocationModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForDayPartModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForHourModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForWeekModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherInfoModel
import java.util.Calendar

const val NOW = "now"

/**
 * Provides ability to map between object instances.
 */
object WeatherMappers {
    /**
     * Converts an instance of [WeatherDto] into a [WeatherInfoModel].
     */
    fun WeatherDto.toWeatherInfo(): WeatherInfoModel {
        val currentHour = this.currentUnixTime.unixTimeToHours()
        val currentWeatherData = currentWeatherData.toCurrentWeatherData(currentHour)
        val hourlyWeatherData =
            getDailyForecast(currentHour, this.currentUnixTime, this.forecastsWeatherData)
        val location = this.location.toLocationModel()
        val weatherDataForWeek = getForecastForTheWeek(this.forecastsWeatherData)

        return WeatherInfoModel(
            currentWeatherForHourModel = currentWeatherData,
            hourlyModelWeatherData = hourlyWeatherData,
            locationModel = location,
            weatherForWeekModel = weatherDataForWeek
        )
    }

    /**
     * Converts an instance of [WeatherDataDto] into a [WeatherForHourModel]
     * with getting [hour] from outside.
     */
    private fun WeatherDataDto.toCurrentWeatherData(hour: Int) = WeatherForHourModel(
        hour = hour,
        temperature.prepareTemperatureForDisplay(),
        feelsLikeTemperature.prepareTemperatureForDisplay(),
        weatherIcon,
        weatherCondition,
        windSpeed,
        windDirection,
        pressureInMM,
        humidity
    )

    /**
     * Converts an instance of [WeatherDataDto] into a [WeatherForHourModel].
     */
    private fun WeatherDataDto.toHourlyWeatherData() = WeatherForHourModel(
        hour,
        temperature.prepareTemperatureForDisplay(),
        feelsLikeTemperature.prepareTemperatureForDisplay(),
        weatherIcon,
        weatherCondition,
        windSpeed,
        windDirection,
        pressureInMM,
        humidity
    )

    /**
     * Converts an instance of [WeatherDataDto] into a [WeatherForDayPartModel].
     */
    private fun WeatherDataDto.toWeatherDataForPartOfDayModel(date: String, dayOfWeek: String) =
        WeatherForDayPartModel(
            maxTemperature = maxTemperature.prepareTemperatureForDisplay(),
            minTemperature = minTemperature.prepareTemperatureForDisplay(),
            avgTemperature = avgTemperature.prepareTemperatureForDisplay(),
            feelsLikeTemperature = feelsLikeTemperature.prepareTemperatureForDisplay(),
            weatherIcon = weatherIcon,
            weatherCondition = weatherCondition,
            windSpeed = windSpeed,
            windDirection = windDirection,
            pressureInMM = pressureInMM,
            humidity = humidity,
            date = date,
            dayOfWeek = dayOfWeek
        )

    /**
     * Converts an instance of [WeatherLocationDto] into a [LocationModel].
     */
    private fun WeatherLocationDto.toLocationModel() = LocationModel(
        city = this.locality.locationName,
        district = this.district?.locationName
    )

    /**
     * Converts an instance of [ForecastsWeatherDto] into a [List<WeatherForHourModel>].
     */
    private fun ForecastsWeatherDto.toListWeatherData(): List<WeatherForHourModel> {
        val hourlyForecast = mutableListOf<WeatherForHourModel>()
        this.hourlyWeatherData.forEach {
            hourlyForecast.add(it.toHourlyWeatherData())
        }
        return hourlyForecast
    }

    /**
     * Get a 24 hour weather forecast starting from the current hour.
     */
    private fun getDailyForecast(
        currentHour: Int,
        unixTime: Long,
        forecasts: List<ForecastsWeatherDto>
    ): List<WeatherForHourModel> {
        val weatherByHoursForDay = mutableListOf<WeatherForHourModel>()
        forecasts[0].toListWeatherData().filter { it.hour >= currentHour }
            .forEach { weatherByHoursForDay.add(it) }
        forecasts[1].toListWeatherData().filter { it.hour <= currentHour }
            .forEach { weatherByHoursForDay.add(it) }
        weatherByHoursForDay[0].date = NOW
        weatherByHoursForDay[HOURS_IN_A_DAY - currentHour].date = unixTime.unixTimeToTomorrowDate()
        return weatherByHoursForDay
    }

    /**
     * Get week weather forecast information.
     */
    private fun getForecastForTheWeek(forecasts: List<ForecastsWeatherDto>): WeatherForWeekModel {
        val morningForecast = mutableListOf<WeatherForDayPartModel>()
        val dayForecast = mutableListOf<WeatherForDayPartModel>()
        val eveningForecast = mutableListOf<WeatherForDayPartModel>()
        val nightForecast = mutableListOf<WeatherForDayPartModel>()
        forecasts.forEach {
            val calendar = it.unixTime.unixTimeToCalendar()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH).toMonthName()
            val date = "$month $day "
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            morningForecast.add(
                it.weatherDataForPartsOfDay.morningWeatherData.toWeatherDataForPartOfDayModel(
                    day.toString(),
                    dayOfWeek.toDayOfWeekName()
                )
            )
            dayForecast.add(
                it.weatherDataForPartsOfDay.dayWeatherData.toWeatherDataForPartOfDayModel(
                    date,
                    dayOfWeek.toDayOfWeekName()
                )
            )
            eveningForecast.add(
                it.weatherDataForPartsOfDay.eveningWeatherData.toWeatherDataForPartOfDayModel(
                    date,
                    dayOfWeek.toDayOfWeekName()
                )
            )
            nightForecast.add(
                it.weatherDataForPartsOfDay.nightWeatherData.toWeatherDataForPartOfDayModel(
                    date,
                    dayOfWeek.toDayOfWeekName()
                )
            )
        }
        return WeatherForWeekModel(
            morningWeather = morningForecast,
            dayWeather = dayForecast,
            eveningWeather = eveningForecast,
            nightWeather = nightForecast
        )
    }

    private fun Int.prepareTemperatureForDisplay(): String {
        return if (this > 0) "+$this" else this.toString()
    }
}
