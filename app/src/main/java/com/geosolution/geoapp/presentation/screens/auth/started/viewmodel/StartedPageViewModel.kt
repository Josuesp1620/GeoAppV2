package com.geosolution.geoapp.presentation.screens.auth.started.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.ViewModelEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.event.ViewModelEventsImpl
import javax.inject.Inject

@HiltViewModel
class StartedPageViewModel @Inject constructor(

) : ViewModel(), ViewModelEvents<Event> by ViewModelEventsImpl() {

    fun goToSigInScreen(){
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(Event.NavigateTo(NavScreen.SignInScreen.route))
        }
    }

    fun goToSigUScreen(){
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(Event.NavigateTo(NavScreen.SignUpScreen.route))
        }
    }

    fun goToHomeScreen(){
        viewModelScope.launch(Dispatchers.IO) {
            sendEvent(Event.NavigateTo(NavScreen.HomeScreen.route))
        }
    }
}