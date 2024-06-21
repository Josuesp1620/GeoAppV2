package com.geosolution.geoapp.presentation.common.connectivity

import kotlinx.coroutines.flow.Flow

interface LocationTracker {

    val flow: Flow<State>

    sealed class State

    object Init : State()

    object Available : State()

    object Unavailable : State()
}
