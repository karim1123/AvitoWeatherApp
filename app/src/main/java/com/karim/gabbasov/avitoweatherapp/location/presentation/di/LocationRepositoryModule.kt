package com.karim.gabbasov.avitoweatherapp.location.presentation.di

import com.karim.gabbasov.avitoweatherapp.location.data.repository.LocationRepositoryImpl
import com.karim.gabbasov.avitoweatherapp.location.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Provides a Hilt module for usage of [LocationRepositoryModule].
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
