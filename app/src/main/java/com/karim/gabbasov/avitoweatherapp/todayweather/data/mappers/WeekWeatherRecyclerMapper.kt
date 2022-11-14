package com.karim.gabbasov.avitoweatherapp.todayweather.data.mappers

import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.NamesOfDayOfWeek
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForDayPartModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeekWeatherRecyclerModel

const val RED_COLOR = "#CD4040"
const val GREY_COLOR = "#4C4C4C"

/**
 * Provides ability to map between object instances.
 */
object WeekWeatherRecyclerMapper {
    /**
     * Converts an instance of List[WeatherForDayPartModel] into a List[WeekWeatherRecyclerModel].
     */
    fun getWeekWeatherModel(forecastForWeek: List<WeatherForDayPartModel>): List<WeekWeatherRecyclerModel> {
        val weekWeather = mutableListOf<WeekWeatherRecyclerModel>()
        forecastForWeek.forEachIndexed { index, item ->
            if (item.dayOfWeek == NamesOfDayOfWeek.Saturday.dayOfWeekName || item.dayOfWeek == NamesOfDayOfWeek.Sunday.dayOfWeekName) {
                if (index == 0)
                    weekWeather.add(
                        WeekWeatherRecyclerModel.TitleItem(
                            textColor = RED_COLOR,
                            date = item.date,
                            dayOfWeek = NamesOfDayOfWeek.Today.dayOfWeekName,
                            maxTemperature = item.maxTemperature,
                            minTemperature = item.minTemperature,
                            weatherIcon = item.weatherIcon,
                            index = index
                        )
                    )
                else
                    weekWeather.add(
                        WeekWeatherRecyclerModel.WeekendItem(
                            textColor = RED_COLOR,
                            date = item.date,
                            dayOfWeek = item.dayOfWeek,
                            maxTemperature = item.maxTemperature,
                            minTemperature = item.minTemperature,
                            weatherIcon = item.weatherIcon,
                            index = index
                        )
                    )
            } else if (index == 0)
                weekWeather.add(
                    WeekWeatherRecyclerModel.TitleItem(
                        textColor = GREY_COLOR,
                        date = item.date,
                        dayOfWeek = NamesOfDayOfWeek.Today.dayOfWeekName,
                        maxTemperature = item.maxTemperature,
                        minTemperature = item.minTemperature,
                        weatherIcon = item.weatherIcon,
                        index = index
                    )
                )
            else
                weekWeather.add(
                    WeekWeatherRecyclerModel.WeekdayItem(
                        date = item.date,
                        dayOfWeek = item.dayOfWeek,
                        maxTemperature = item.maxTemperature,
                        minTemperature = item.minTemperature,
                        weatherIcon = item.weatherIcon,
                        index = index
                    )
                )
        }
        return weekWeather
    }
}
