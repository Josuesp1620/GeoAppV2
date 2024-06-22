package com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel

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
import com.geosolution.geoapp.data.remote.api.auth.SignUpRequest
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<SignUpState> = mutableStateOf(SignUpState())
    val state: SignUpState by _state

    fun signUp(fullname : String, username : String ,email : String, password: String) {
        signUpUseCase(SignUpRequest(fullname = fullname, username = username, email = email, password = password))
            .onLoading {
                _state.update { SignUpState(loading = true) }
            }
            .onEmpty {
                _state.update { SignUpState(loading = false) }
            }
            .onError {
                Log.d("SignUpViewModel", this)
                _state.update { SignUpState(loading = false) }
            }
            .onSuccess {
                _state.update { SignUpState(loading = false) }
//                sendEvent(
//                    Event.NavigateTo(HomeScreen())
//                )
            }
            .launchIn(viewModelScope)
    }
}