package com.karim.gabbasov.avitoweatherapp.todayweather.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents location.
 */
data class WeatherLocationDto(
    @field:Json(name = "locality")
    val locality: LocationNameDto,
    @field:Json(name = "district")
    val district: LocationNameDto?
)
