package com.geosolution.geoapp.presentation.screens.client.viewmodel

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
import com.geosolution.geoapp.domain.use_case.SignUpUseCase
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class CreateClientViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<CreateClientState> = mutableStateOf(CreateClientState())
    val state: CreateClientState by _state

    fun create(fullname : String, username : String ,email : String) {

    }
}