package com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel

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
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.domain.use_case.auth.AuthSaveCacheUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSignUpUseCase
import com.geosolution.geoapp.domain.use_case.user.UserSaveStoreUseCase
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authSignUp: AuthSignUpUseCase,
    private val userSaveCacheUseCase: UserSaveStoreUseCase,
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<SignUpState> = mutableStateOf(SignUpState())
    val state: SignUpState by _state

    fun signUp(name: String, fullname : String, email : String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userSaveCacheUseCase(User(name=name, fullName = fullname, image = "", email = email, password = password))
            sendEvent(
                Event.NavigateTo(NavScreen.SignInScreen.route)
            )
        }


//        authSignUp(SignUpRequest(fullname = fullname, username = username, email = email, password = password))
//            .onLoading {
//                _state.update { SignUpState(loading = true) }
//            }
//            .onEmpty {
//                _state.update { SignUpState(loading = false) }
//            }
//            .onError {
//                _state.update { SignUpState(loading = false) }
//            }
//            .onSuccess {
//                _state.update { SignUpState(loading = false) }
////                sendEvent(
////                    Event.NavigateTo(NavScreen.SignInScreen.route)
////                )
//            }
//            .launchIn(viewModelScope)
    }
}