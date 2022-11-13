package com.karim.gabbasov.avitoweatherapp.location.data.mappers

import com.karim.gabbasov.avitoweatherapp.location.data.remote.LocationDto
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressHintsModel
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressModel

/**
 * Provides ability to map between object instances.
 */
object LocationMappers {
    /**
     * Converts an instance of [LocationDto] into a [AddressHintsModel].
     */
    fun LocationDto.toAddressHintsModel(): AddressHintsModel {
        val addresses = mutableListOf<AddressModel>()
        this.locationData.forEach {
            if (it.locationName.isNotEmpty())
                addresses.add(
                    AddressModel(
                        it.locationName,
                        it.locationData.latitude,
                        it.locationData.longitude
                    )
                )
        }
        return AddressHintsModel(addresses)
    }
}
