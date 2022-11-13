package com.karim.gabbasov.avitoweatherapp.location.domain.repository

import com.karim.gabbasov.avitoweatherapp.util.Resource
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressHintsModel

/**
 * Interface of repository to chose weather location.
 */
interface LocationRepository {
    /**
     * Retrieve the address hints by [query].
     */
    suspend fun getAddressHints(query: String): Resource<AddressHintsModel>
}
