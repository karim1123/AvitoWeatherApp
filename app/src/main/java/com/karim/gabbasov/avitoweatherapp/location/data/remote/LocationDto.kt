package com.karim.gabbasov.avitoweatherapp.location.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents hints for addresses.
 */
data class LocationDto(
    @field:Json(name = "suggestions")
    val locationData: List<LocationSuggestionsDto>
)
