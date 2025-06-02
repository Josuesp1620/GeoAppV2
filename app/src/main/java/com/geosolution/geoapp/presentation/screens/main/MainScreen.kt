package com.geosolution.geoapp.presentation.screens.main

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.geosolution.geoapp.presentation.ui.utils.permissions.PermissionState
import com.geosolution.geoapp.presentation.ui.utils.permissions.rememberPermissionRequestManager

@Composable
fun MainScreen(
    navigationController : NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val (permissionState, permissionLauncher) = rememberPermissionRequestManager { permissionsResultMap ->
        Log.d("MainScreenPermissions", "Permission results: $permissionsResultMap")
        // Potentially trigger a recomposition or update a local state if needed
        // based on these results to ensure UI reacts immediately.
        // The permissionState itself should update from within rememberPermissionRequestManager.
    }

    val requiredPermissions = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(key1 = permissionState.allPermissionsGranted) { // Re-run if allPermissionsGranted changes
        if (!permissionState.allPermissionsGranted) {
            // Check if we should show rationale here before launching, if desired.
            // For this subtask, directly launch.
            Log.d("MainScreenPermissions", "Not all permissions granted. Launching request.")
            permissionLauncher.launch(requiredPermissions)
        } else {
            Log.d("MainScreenPermissions", "All permissions already granted.")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
        }
    ) { paddingValues ->
        Navigation(
            authState = state.authState,
            networkState = state.networkState,
            navigationController = navigationController,
            user = state.user,
            permissionState = permissionState // Pass the state down
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