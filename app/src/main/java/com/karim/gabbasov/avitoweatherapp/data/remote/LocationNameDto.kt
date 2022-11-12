package com.karim.gabbasov.avitoweatherapp.data.remote

import com.squareup.moshi.Json

/**
 * DTO that represents name of location.
 */
data class LocationNameDto(
    @field:Json(name = "name")
    val locationName: String
)
