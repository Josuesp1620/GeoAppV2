package com.geosolution.geoapp.presentation.screens.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.use_case.auth.AuthGetCacheUseCase
import com.geosolution.geoapp.domain.use_case.location.LocationDeleteCacheUseCase
import com.geosolution.geoapp.domain.use_case.user.UserGetStoreUseCase
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkTracker: NetworkTracker,
    private val authGetCache: AuthGetCacheUseCase,
    private val userGetStoreUseCase: UserGetStoreUseCase,
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
            authGetCache().catch { e ->
                _state.update { state -> state.copy(snackbar = e.message ?: "") }
            }.collectLatest { auth ->
                Log.d("MainViewModel", "value: ${auth != null}")

                if (auth != null) {
                    Log.d("MainViewModel", "auth: ${auth}")

                    try {
                        // Ejecuta la operación de Room en el contexto IO
                        withContext(Dispatchers.IO) {
                            userGetStoreUseCase(auth.accessToken!!).catch { e ->
                                Log.d("MainViewModel", "error: ${e}")
                            }.collectLatest { user ->
                                Log.d("MainViewModel", "user: ${user?.name}")

                                if (user != null) {
                                    withContext(Dispatchers.Main) {
                                        _state.update { state -> state.copy(authState = AuthState.Authenticated(auth)) }
                                        _state.update { state -> state.copy(user = user) }
                                        eventFlow.emit(Event.NavigateTo(NavScreen.HomeScreen.route))
                                    }
                                } else {
                                    Log.d("MainViewModel", "user is null")
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("MainViewModel", "error: ${e}")
                    }

                } else {
                    _state.update { state -> state.copy(authState = AuthState.Unauthenticated) }
                    // eventFlow.emit(Event.NavigateTo(NavScreen.StartedPageScreen.route))
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