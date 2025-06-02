package com.geosolution.geoapp.presentation.screens.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.data.service.LocationBackgroundService
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.use_case.client.ClientGetAllStoreUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationDeleteCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val clientGetAllStoreUseCase: ClientGetAllStoreUseCase,
    private val locatioSaveCache: LocationSaveCacheUseCase, // Consider renaming for consistency e.g., locationSaveCacheUseCase
    private val locationDeleteCacheUseCase: LocationDeleteCacheUseCase,
    private val locationGetCache: LocationGetCacheUseCase // Consider renaming for consistency e.g., locationGetCacheUseCase

) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    @SuppressLint("StaticFieldLeak")
    private val contextLiveData = context

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var locationCollectionJob: Job? = null

    init {
        clientGetAllStore()
        loadLocationCurrentState()
    }

    private fun clientGetAllStore() {
        clientGetAllStoreUseCase().onEach {
            _state.update { state -> state.copy(clientList = it) }
        }.launchIn(viewModelScope)
    }

    private fun startUpdates() {
        val intent = Intent(contextLiveData, LocationBackgroundService::class.java).apply {
            action = LocationBackgroundService.ACTION_START_LOCATION_SERVICE
        }
        contextLiveData.startService(intent)
    }

    private fun stopUpdates() {
        val intent = Intent(contextLiveData, LocationBackgroundService::class.java).apply {
            action = LocationBackgroundService.ACTION_STOP_LOCATION_SERVICE
        }
        contextLiveData.startService(intent)
    }

    fun activeLocation(checked: Boolean) {
        if (checked) {
            _state.update { it.copy(isLocationCurrent = true) }
            startUpdates()
            startObservingLocationCache()
        } else {
            stopUpdates()
            locationCollectionJob?.cancel()
            clearLocationCache() // This will also update state for location = null
            _state.update { it.copy(isLocationCurrent = false, location = null) }
        }
    }

    private fun startObservingLocationCache() {
        locationCollectionJob?.cancel() // Cancel any previous job
        locationCollectionJob = viewModelScope.launch {
            locationGetCache().collectLatest { location ->
                if (location != null) {
                    Log.d("HomeViewModel", "New location observed: Lat: ${location.latitude}, Lon: ${location.longitude}, Bearing: ${location.bearing}")
                    _state.update { currentState ->
                        currentState.copy(location = location, isLocationCurrent = true)
                    }
                } else {
                    // This case might occur if cache is cleared while still observing
                    Log.d("HomeViewModel", "Observed null location from cache.")
                    _state.update { currentState ->
                        currentState.copy(location = null) // isLocationCurrent will be handled by activeLocation(false) or init
                    }
                }
            }
        }
    }

    // onLocationUpdate seems to be unused now that the service handles saving.
    // private fun onLocationUpdate(location: Location) {
    //     viewModelScope.launch {
    //         locatioSaveCache(location)
    //     }
    // }

    private fun clearLocationCache() {
        viewModelScope.launch {
            locationDeleteCacheUseCase()
            // State update for location = null is handled here, or in activeLocation(false)
            _state.update { state -> state.copy(location = null) }
        }
    }

    // updateLocationCurrentState is now handled directly within activeLocation or init
    // private fun updateLocationCurrentState(locationState: Boolean) {
    //     viewModelScope.launch {
    //         _state.update { state ->
    //             state.copy(isLocationCurrent = locationState)
    //         }
    //     }
    // }

    private fun loadLocationCurrentState() {
        viewModelScope.launch {
            val initialLocation = locationGetCache().firstOrNull()
            if (initialLocation != null) {
                Log.d("HomeViewModel", "Initial location loaded: Lat: ${initialLocation.latitude}, Lon: ${initialLocation.longitude}, Bearing: ${initialLocation.bearing}")
                _state.update { currentState ->
                    currentState.copy(isLocationCurrent = true, location = initialLocation)
                }
                // If isLocationCurrent is true, also call startUpdates() to ensure service is running
                // And call startObservingLocationCache() to sync with UI
                startUpdates() // Ensures service is (re)started if app was killed and cache indicates it should be running
                startObservingLocationCache()
            } else {
                _state.update { currentState ->
                    currentState.copy(isLocationCurrent = false, location = null)
                }
            }
        }
    }
}