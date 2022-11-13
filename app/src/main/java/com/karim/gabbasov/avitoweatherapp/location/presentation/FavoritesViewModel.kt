package com.karim.gabbasov.avitoweatherapp.location.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karim.gabbasov.avitoweatherapp.util.Resource
import com.karim.gabbasov.avitoweatherapp.location.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to aid user to chose weather location
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _locationStatus: MutableStateFlow<LocationState> =
        MutableStateFlow(LocationState.Success())
    val locationStatus = _locationStatus.asStateFlow()

    fun loadAddressHints(query: String) {
        viewModelScope.launch(dispatcher) {
            when (val result = repository.getAddressHints(query)) {
                is Resource.Success ->
                    _locationStatus.value = LocationState.Success(result.data)
                is Resource.Error ->
                    _locationStatus.value = LocationState.Error(result.message)
            }
        }
    }
}
