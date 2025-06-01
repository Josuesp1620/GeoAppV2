package com.geosolution.geoapp.presentation.screens.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
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

    }
}