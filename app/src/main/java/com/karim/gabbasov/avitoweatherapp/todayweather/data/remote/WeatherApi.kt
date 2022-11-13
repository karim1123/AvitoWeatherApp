package com.karim.gabbasov.avitoweatherapp.todayweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val GET_WEATHER_LINK = "v2/forecast?hourly=temperature_2m&hours=true&limit=7&extra=true&lang=en_US"

/**
 * Method relating to getting information about weather forecast from the api.
 */
interface WeatherApi {
    @GET(GET_WEATHER_LINK)
    suspend fun getWeatherData(
        @Header("X-Yandex-API-key") token: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherDto
}
