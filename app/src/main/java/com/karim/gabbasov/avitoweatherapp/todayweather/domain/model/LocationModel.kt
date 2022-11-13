package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

/**
 * Model containing location information.
 */
data class LocationModel(
    val city: String,
    val district: String? = null
)
