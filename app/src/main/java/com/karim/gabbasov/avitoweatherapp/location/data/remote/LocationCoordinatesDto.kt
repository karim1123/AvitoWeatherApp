package com.karim.gabbasov.avitoweatherapp.location.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents location coordinates.
 */
data class LocationCoordinatesDto(
    @field:Json(name = "geo_lat")
    val latitude: Double,
    @field:Json(name = "geo_lon")
    val longitude: Double
)
