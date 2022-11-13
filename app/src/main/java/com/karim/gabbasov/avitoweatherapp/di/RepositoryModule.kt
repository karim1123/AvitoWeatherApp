package com.karim.gabbasov.avitoweatherapp.di

import com.karim.gabbasov.avitoweatherapp.todayweather.data.repository.WeatherRepositoryImpl
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Provides a Hilt module for usage of [WeatherRepository].
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
