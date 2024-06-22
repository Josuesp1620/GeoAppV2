package com.geosolution.geoapp.presentation.screens.auth.signin.viewModel

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
import com.geosolution.geoapp.domain.use_case.auth.AuthSaveCacheUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSignInUseCase
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
    private val authSaveCache: AuthSaveCacheUseCase,
    private val authSignIn: AuthSignInUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<SignInState> = mutableStateOf(SignInState())
    val state: SignInState by _state

    fun signIn(email : String, password: String) {
        authSignIn(SignInRequest(email = email, password = password))
            .onLoading {
                _state.update { SignInState(loading = true) }
            }
            .onEmpty {
                _state.update { SignInState(loading = false) }
            }
            .onError {
                _state.update { SignInState(loading = false) }

            }
            .onSuccess {
                authSaveCache(this)
                sendEvent(
                    Event.NavigateTo(NavScreen.HomeScreen.route)
                )
            }
            .launchIn(viewModelScope)
    }
}