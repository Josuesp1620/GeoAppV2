package com.geosolution.geoapp.presentation.screens.client.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.use_case.client.ClientCreateStoreUseCase
import com.geosolution.geoapp.presentation.screens.auth.signin.viewModel.SignInState
import com.geosolution.geoapp.presentation.screens.main.viewmodel.AuthState
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.utils.StateUtils.update
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class CreateClientViewModel @Inject constructor(
    private val clientCreateStore: ClientCreateStoreUseCase
) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    private val _state: MutableState<CreateClientState> = mutableStateOf(CreateClientState())
    val state: CreateClientState by _state

    override val eventFlow = MutableSharedFlow<Event>()


    fun create(name : String, fullName : String, vat: String, businessName: String, address: String, coordinates: String) {
        val client = Client(
            name = name,
            fullName = fullName,
            vat = vat,
            businessName = businessName,
            address = address,
            coordinates = coordinates,
            image = null
        )
        viewModelScope.launch {
            _state.update { CreateClientState(loading = true) }
            clientCreateStore(client)
            delay(3000L)
            eventFlow.emit(Event.NavigateTo(NavScreen.HomeScreen.route))
        }


    }
}