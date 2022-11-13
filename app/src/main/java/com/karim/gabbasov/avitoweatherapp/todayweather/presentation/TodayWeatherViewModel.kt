package com.karim.gabbasov.avitoweatherapp.todayweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.location.LocationTracker
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.CoordinatesModel
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.repository.WeatherRepository
import com.karim.gabbasov.avitoweatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ERROR_MESSAGE =
    "Couldn't retrieve location. Make sure to grant permission and enable GPS."

/**
 * ViewModel to aid with downloading weather forecast data.
 */
@HiltViewModel
class TodayWeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _todayWeatherStatus: MutableStateFlow<WeatherState> =
        MutableStateFlow(WeatherState.Success())
    val todayWeatherStatus = _todayWeatherStatus.asStateFlow()

    /**
     * Executes a downloading weather data request ,the result is posted to
     * [todayWeatherStatus].
     */
    fun loadWeatherInfo(chosenLocation: CoordinatesModel?) {
        viewModelScope.launch(dispatcher) {
            _todayWeatherStatus.value = WeatherState.Loading
            val location: CoordinatesModel? = chosenLocation ?: locationTracker.getCurrentLocation()
            location?.let { location ->
                when (
                    val result =
                        repository.getWeatherData(
                            location.latitude,
                            location.longitude
                        )
                ) {
                    is Resource.Success -> {
                        _todayWeatherStatus.value = WeatherState.Success(result.data)
                    }
                    is Resource.Error -> {
                        _todayWeatherStatus.value = WeatherState.Error(result.message)
                    }
                }
            } ?: kotlin.run {
                _todayWeatherStatus.value =
                    WeatherState.Error(ERROR_MESSAGE)
            }
        }
    }
}
