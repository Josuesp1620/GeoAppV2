package com.geosolution.geoapp.presentation.screens.auth.signin.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.data.local.entity.asDatabaseEntity
import com.geosolution.geoapp.domain.model.Auth
import com.geosolution.geoapp.domain.use_case.auth.AuthSaveCacheUseCase
import com.geosolution.geoapp.domain.use_case.auth.AuthSignInUseCase
import com.geosolution.geoapp.domain.use_case.user.UserGetStoreUseCase
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authSaveCache: AuthSaveCacheUseCase,
    private val authSignIn: AuthSignInUseCase,
    private val userGetStoreUseCase: UserGetStoreUseCase,
    private val authSaveCacheUseCase: AuthSaveCacheUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<SignInState> = mutableStateOf(SignInState())
    val state: SignInState by _state

    fun signIn(email : String, password: String) {
        _state.update { state.copy(loading = true) }

        userGetStoreUseCase(email).onEach { user ->
            val userDomain = user?.asDatabaseEntity()?.asDomain()
            Log.d("SignInViewModel", "$userDomain")
            Log.d("SignInViewModel", "email :$email - password: $password")
            if(userDomain!= null){
                Log.d("SignInViewModel", "Valdate: ${userDomain.password == password && userDomain.email == email}")

                if(userDomain.password == password && userDomain.email == email){
                    authSaveCacheUseCase(Auth(userDomain.email, userDomain.email))
                    _state.update { state.copy(loading=false) }
                    sendEvent(
                        Event.NavigateTo(NavScreen.StartedPageScreen.route)
                    )
                }else {
                    _state.update { state.copy(loading=false) }
                }
            }else{
                _state.update { state.copy(loading=false) }

            }


        }.launchIn(viewModelScope)
//        authSignIn(SignInRequest(email = email, password = password))
//            .onLoading {
//                _state.update { SignInState(loading = true) }
//            }
//            .onEmpty {
//                _state.update { SignInState(loading = false) }
//            }
//            .onError {
//                _state.update { SignInState(loading = false) }
//
//            }
//            .onSuccess {
//                authSaveCache(this)
//                sendEvent(
//                    Event.NavigateTo(NavScreen.HomeScreen.route)
//                )
//            }
//            .launchIn(viewModelScope)
    }
}