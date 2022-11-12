package com.karim.gabbasov.avitoweatherapp.domain.repository

import com.karim.gabbasov.avitoweatherapp.domain.util.Resource
import com.karim.gabbasov.avitoweatherapp.domain.weather.WeatherInfoModel

/**
 * Interface of repository to interact weather forecast data.
 */
interface WeatherRepository {
    /**
     * Retrieve the information weather forecasts by coordinates [lat], [long].
     */
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfoModel>
}
