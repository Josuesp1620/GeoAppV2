package com.geosolution.geoapp.presentation.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.presentation.screens.main.components.NetworkStatus
import com.geosolution.geoapp.presentation.screens.main.viewmodel.MainViewModel
import com.geosolution.geoapp.presentation.screens.navigations.Navigation
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.collectWithLifecycle

@Composable
fun MainScreen(
    navigationController : NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
        }
    ) { paddingValues ->
        Navigation(
            authState = state.authState,
            networkState = state.networkState,
            navigationController = navigationController,
            user = state.user
        )
        NetworkStatus(
            stateProvider = { state.networkState },
            modifier = Modifier.padding(paddingValues = paddingValues)
        )
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.NavigateTo -> {
                navigationController.navigate(it.screen)
            }
        }
    }
}