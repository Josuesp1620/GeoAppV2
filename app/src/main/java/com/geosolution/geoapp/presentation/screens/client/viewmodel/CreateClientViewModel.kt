package com.geosolution.geoapp.presentation.screens.client.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
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