package com.karim.gabbasov.avitoweatherapp.data.repository

import com.karim.gabbasov.avitoweatherapp.data.mappers.WeatherMappers.toWeatherInfo
import com.karim.gabbasov.avitoweatherapp.data.remote.WeatherApi
import com.karim.gabbasov.avitoweatherapp.domain.repository.WeatherRepository
import com.karim.gabbasov.avitoweatherapp.domain.util.Resource
import com.karim.gabbasov.avitoweatherapp.domain.weather.WeatherInfoModel
import javax.inject.Inject

const val ERROR_MESSAGE = "An unknown error occurred."

/**
 * Repository to interact weather forecast data.
 */
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    /**
     * Retrieve the information weather forecasts by coordinates [lat], [long].
     */
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfoModel> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    token = "bd20d8ca-f254-46f5-b340-ae866ad7ef8f",
                    latitude = lat,
                    longitude = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: ERROR_MESSAGE)
        }
    }
}
