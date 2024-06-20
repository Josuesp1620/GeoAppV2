package com.geosolution.geoapp.presentation.screens.auth.signin.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.core.utils.onEmpty
import com.geosolution.geoapp.core.utils.onError
import com.geosolution.geoapp.core.utils.onLoading
import com.geosolution.geoapp.core.utils.onSuccess
import com.geosolution.geoapp.data.remote.api.auth.SignInRequest
import com.geosolution.geoapp.domain.use_case.CacheAuthUseCase
import com.geosolution.geoapp.domain.use_case.SignInUseCase
import com.geosolution.geoapp.presentation.screens.home.HomeScreen
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val cacheAuthUseCase: CacheAuthUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<SignInState> = mutableStateOf(SignInState())
    val state: SignInState by _state

    fun signIn(email : String, password: String) {
        Log.d("SignInViewModel", "$email - $password")
        signInUseCase(SignInRequest(email = email, password = password))
            .onLoading {
                _state.update { SignInState(loading = true) }
                Log.d("SignInViewModel", "onLoading")
                sendEvent(
                    Event.NavigateTo(NavScreen.HomeScreen.route)
                )
            }
            .onEmpty {
                _state.update { SignInState(loading = false) }
                Log.d("SignInViewModel", "onEmpty")
                sendEvent(
                    Event.NavigateTo(NavScreen.HomeScreen.route)
                )
            }
            .onError {
                _state.update { SignInState(loading = false) }
                Log.d("SignInViewModel", "onError")
                sendEvent(
                    Event.NavigateTo(NavScreen.HomeScreen.route)
                )

            }
            .onSuccess {
                cacheAuthUseCase(this)
                Log.d("SignInViewModel", "onSuccess")

                sendEvent(
                    Event.NavigateTo(NavScreen.HomeScreen.route)
                )
            }
            .launchIn(viewModelScope)
    }
}