package com.karim.gabbasov.avitoweatherapp.todayweather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model containing the coordinates of location.
 */
@Parcelize
data class CoordinatesModel(
    val latitude: Double,
    val longitude: Double
) : Parcelable
