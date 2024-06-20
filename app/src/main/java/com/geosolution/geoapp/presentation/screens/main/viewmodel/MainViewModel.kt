package com.geosolution.geoapp.presentation.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.domain.use_case.GetCacheAuthUseCase
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkTracker: NetworkTracker,
    private val getCacheAuthUseCase: GetCacheAuthUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    override val eventFlow = MutableSharedFlow<Event>()

    init {
        loadAuthState()
        loadNetworkState()
    }

    private fun loadAuthState() {
        viewModelScope.launch {
            getCacheAuthUseCase().catch { e ->
                _state.update { state -> state.copy(snackbar = e.message ?: "") }
            }.collectLatest { auth ->
                delay(5000)
                if (auth != null) {
                    _state.update { state -> state.copy(authState = AuthState.Authenticated(auth)) }
                    eventFlow.emit(Event.NavigateTo(NavScreen.HomeScreen.route))
                } else {
                    _state.update { state -> state.copy(authState = AuthState.Unauthenticated) }
                    eventFlow.emit(Event.NavigateTo(NavScreen.HomeScreen.route))
                }
            }
        }
    }

    private fun loadNetworkState() {
        viewModelScope.launch {
            networkTracker.flow
                .catch { e ->
                    _state.update { state -> state.copy(snackbar = e.message ?: "") }
                }
                .collectLatest { networkState ->
                    _state.update { state -> state.copy(networkState = networkState) }
                }
        }
    }
}