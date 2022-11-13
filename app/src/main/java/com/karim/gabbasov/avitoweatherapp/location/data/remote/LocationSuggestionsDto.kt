package com.karim.gabbasov.avitoweatherapp.location.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents location data.
 */
data class LocationSuggestionsDto(
    @field:Json(name = "unrestricted_value")
    val locationName: String,
    @field:Json(name = "data")
    val locationData: LocationCoordinatesDto
)
