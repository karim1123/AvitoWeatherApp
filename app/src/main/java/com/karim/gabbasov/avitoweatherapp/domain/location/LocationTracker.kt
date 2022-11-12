package com.karim.gabbasov.avitoweatherapp.domain.location

import com.karim.gabbasov.avitoweatherapp.domain.weather.CoordinatesModel

/**
 * Util interface to aid to get the user's current location.
 */
interface LocationTracker {
    suspend fun getCurrentLocation(): CoordinatesModel?
}
