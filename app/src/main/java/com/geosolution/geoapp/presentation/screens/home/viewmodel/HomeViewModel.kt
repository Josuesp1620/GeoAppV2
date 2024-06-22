package com.geosolution.geoapp.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locatioSaveCache: LocationSaveCacheUseCase,
    private val locationGetCache: LocationGetCacheUseCase

) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            loadLocationCurrentState()
        }
    }

    private fun loadLocationCurrentState() {
        viewModelScope.launch {
            locationGetCache().catch { e ->
                _state.update { state -> state.copy(snackbar = e.message ?: "") }
            }.collectLatest { location ->
                if (location != null) {
                    _state.update { state -> state.copy(isLocationCurrent = true) }
                } else {
                    _state.update { state -> state.copy(isLocationCurrent = false) }
                }
            }
        }
    }

    fun updateLocationCurrentState(locationState: Boolean) {
        viewModelScope.launch {
            _state.update { state -> state.copy(isLocationCurrent = locationState) }
        }
    }

    fun updateLocationCurrent(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            locatioSaveCache(Location(latitude.toString(),  longitude.toString()))
        }
    }
}