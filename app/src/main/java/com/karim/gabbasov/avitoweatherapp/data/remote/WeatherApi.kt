package com.karim.gabbasov.avitoweatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val GET_WEATHER_LINK = "v2/forecast?lat=56.81940684496419&lon=53.145196970370534&hours=true&limit=7&extra=true&lang=en_US"

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
