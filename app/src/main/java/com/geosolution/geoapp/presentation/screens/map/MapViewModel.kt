package com.geosolution.geoapp.presentation.screens.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.core.location.LocationService
import com.geosolution.geoapp.domain.model.Location
import com.geosolution.geoapp.domain.use_case.client.ClientGetAllStoreUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationDeleteCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationSaveCacheUseCase
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeState
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import com.geosolution.geolocation.GeoLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    @SuppressLint("StaticFieldLeak")
    private val contextLiveData = context

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    init {
        startUpdates()
    }

    private fun startUpdates() {
        GeoLocation.startLocationUpdates(this.contextLiveData).observeForever { locationResult ->
            viewModelScope.launch {
                _state.update { state -> state.copy(location=locationResult.location!!) }
            }
        }
    }
}