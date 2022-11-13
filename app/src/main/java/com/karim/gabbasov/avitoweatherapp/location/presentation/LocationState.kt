package com.karim.gabbasov.avitoweatherapp.location.presentation

import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressHintsModel

/**
 * Provides the status of address hints.
 */
sealed class LocationState {
    /**
     * Response is a success, containing [addressHintsModel].
     */
    data class Success(
        val addressHintsModel: AddressHintsModel? = null,
    ) : LocationState()
    /**
     * Response was a failure, with the [exception] containing information on why.
     */
    data class Error(val exception: String?) : LocationState()
}
