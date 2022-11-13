package com.karim.gabbasov.avitoweatherapp.todayweather.domain.location

import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.CoordinatesModel

/**
 * Util interface to aid to get the user's current location.
 */
interface LocationTracker {
    suspend fun getCurrentLocation(): CoordinatesModel?
}
