package com.karim.gabbasov.avitoweatherapp.di

import com.karim.gabbasov.avitoweatherapp.data.location.LocationTrackerImpl
import com.karim.gabbasov.avitoweatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Provides a Hilt module for usage of [LocationTracker].
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker
}
