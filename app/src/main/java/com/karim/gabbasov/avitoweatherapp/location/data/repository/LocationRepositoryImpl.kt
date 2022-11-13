package com.karim.gabbasov.avitoweatherapp.location.data.repository

import com.karim.gabbasov.avitoweatherapp.util.Resource
import com.karim.gabbasov.avitoweatherapp.location.data.mappers.LocationMappers.toAddressHintsModel
import com.karim.gabbasov.avitoweatherapp.location.data.remote.LocationApi
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressHintsModel
import com.karim.gabbasov.avitoweatherapp.location.domain.repository.LocationRepository
import javax.inject.Inject

const val TOKEN = "Token df005ca7bb8d52310d99f3b73276509ef0691cac"
const val ERROR_MESSAGE = "An unknown error occurred."

/**
 * Repository to chose weather location.
 */
class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApi
) : LocationRepository {
    /**
     * Retrieve the address hints by [query].
     */
    override suspend fun getAddressHints(query: String): Resource<AddressHintsModel> {
        return try {
            Resource.Success(
                data = api.getLocationsData(
                    query = query,
                    token = TOKEN
                ).toAddressHintsModel()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: ERROR_MESSAGE)
        }
    }
}
