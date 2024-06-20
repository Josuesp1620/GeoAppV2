package com.geosolution.geoapp.presentation.ui.utils.event

sealed class Event {
    class NavigateTo(val screen: String) : Event()
}
