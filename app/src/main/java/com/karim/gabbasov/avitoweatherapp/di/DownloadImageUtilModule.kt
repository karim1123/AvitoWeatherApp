package com.karim.gabbasov.avitoweatherapp.di

import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Provides a Hilt module for usage of [DownloadImageUtil].
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadImageUtilModule {
    @Binds
    @Singleton
    abstract fun bindDownloadImageUtil(
        downloadImageUtilImpl: DownloadImageUtilImpl
    ): DownloadImageUtil
}
