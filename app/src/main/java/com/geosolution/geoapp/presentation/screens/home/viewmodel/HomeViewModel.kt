package com.geosolution.geoapp.presentation.screens.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.geosolution.geoapp.data.service.LocationBackgroundService
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.use_case.client.ClientGetAllStoreUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationDeleteCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
    private val locatioSaveCache: LocationSaveCacheUseCase,
    private val locationDeleteCacheUseCase: LocationDeleteCacheUseCase,
    private val locationGetCache: LocationGetCacheUseCase

) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    @SuppressLint("StaticFieldLeak")
    private val contextLiveData = context

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        clientGetAllStore()
        loadLocationCurrentState()
    }

    private fun clientGetAllStore() {
        clientGetAllStoreUseCase().onEach {
            _state.update { state -> state.copy(clientList= it) }

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
        updateLocationCurrentState(checked)


        if (checked) {
            startUpdates()
        } else {
            stopUpdates()
            clearLocationCache()
        }
    }

    private fun onLocationUpdate(location: Location) {
        viewModelScope.launch {
            locatioSaveCache(location)
        }
    }

    private fun clearLocationCache() {
        viewModelScope.launch {
            _state.update { state -> state.copy(location=null) }
            locationDeleteCacheUseCase()
        }
    }

    private fun updateLocationCurrentState(locationState: Boolean) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(isLocationCurrent = locationState)
            }
        }
    }

    private fun loadLocationCurrentState() {
        viewModelScope.launch {
            locationGetCache().catch { e ->
                _state.update { state -> state.copy(snackbar = e.message ?: "") }
                }.collectLatest { location ->
                if (location != null) {
                    startUpdates()
                    _state.update { state -> state.copy(isLocationCurrent = true) }
                } else {
                    clearLocationCache()
                    _state.update { state -> state.copy(isLocationCurrent = false) }
                }
            }
        }
    }
}