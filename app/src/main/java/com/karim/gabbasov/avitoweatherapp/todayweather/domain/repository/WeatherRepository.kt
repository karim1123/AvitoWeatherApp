package com.karim.gabbasov.avitoweatherapp.todayweather.domain.repository

import com.karim.gabbasov.avitoweatherapp.util.Resource
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherInfoModel

/**
 * Interface of repository to interact weather forecast data.
 */
interface WeatherRepository {
    /**
     * Retrieve the information weather forecasts by coordinates [lat], [long].
     */
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfoModel>
}
