package com.geosolution.geoapp.presentation.screens.map

import android.annotation.SuppressLint
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val locationGetCache: LocationGetCacheUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    @SuppressLint("StaticFieldLeak")
    private val contextLiveData = context

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    init {
        // startUpdates() // Original call
        observeLocationCache() // New call
    }

    // Remove private fun startUpdates() {} if it's empty

    private fun observeLocationCache() {
        viewModelScope.launch {
            locationGetCache().collectLatest { location -> // This is Flow<com.geosolution.geoapp.domain.model.Location?>
                if (location != null) {
                    Log.d("MapViewModel", "Observed location: Lat: ${location.latitude}, Lon: ${location.longitude}, Bearing: ${location.bearing}")
                    _state.update { currentState -> currentState.copy(location = location) }
                } else {
                    Log.d("MapViewModel", "Observed null location from cache.")
                    // Optionally update state to null if needed, or let it persist last known
                     _state.update { currentState -> currentState.copy(location = null) }
                }
            }
        }
    }
}