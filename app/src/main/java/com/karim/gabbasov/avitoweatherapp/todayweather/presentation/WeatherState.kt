package com.karim.gabbasov.avitoweatherapp.todayweather.presentation

import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherInfoModel

/**
 * Provides the status of downloading weather forecast data request.
 */
sealed class WeatherState {
    /**
     * Response is a success, containing [weatherInfoModel].
     */
    data class Success(
        val weatherInfoModel: WeatherInfoModel? = null,
    ) : WeatherState()
    /**
     * Response was a failure, with the [exception] containing information on why.
     */
    data class Error(val exception: String?) : WeatherState()
    /**
     * Response in progress.
     */
    object Loading : WeatherState()
}
