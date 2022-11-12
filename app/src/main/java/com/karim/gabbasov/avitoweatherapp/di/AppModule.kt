package com.karim.gabbasov.avitoweatherapp.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karim.gabbasov.avitoweatherapp.data.remote.WeatherApi
import com.karim.gabbasov.avitoweatherapp.data.util.InternetLinks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * Dagger [Module] for generic application items.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the [WeatherApi] for the application.
     */
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(InternetLinks.BASE_URL.link)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    /**
     * Provides the [FusedLocationProviderClient] for the application.
     */
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    /**
     * Provides the [ImageLoader] for the application.
     */
    @Provides
    @Singleton
    fun provideSvgImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    /**
     * Provides the [Dispatchers.IO] for the application.
     */
    @Provides
    @Singleton
    fun provideDispatcher() = Dispatchers.IO
}
