package com.karim.gabbasov.avitoweatherapp.location.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val GET_LOCATION_LINK = "/suggestions/api/4_1/rs/suggest/address?language=en&geoLocation=true"

/**
 * Method relating to getting hints for addresses.
 */
interface LocationApi {
    @GET(GET_LOCATION_LINK)
    suspend fun getLocationsData(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): LocationDto
}
