package com.geosolution.geoapp.presentation.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.geosolution.geoapp.core.location.LocationUtils
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.core.location.RunUtils
import com.geosolution.geoapp.core.location.RunUtils.hasAllPermission
import com.geosolution.geoapp.core.location.RunUtils.hasLocationPermission
import com.geosolution.geoapp.core.location.RunUtils.openAppSetting
import com.geosolution.geoapp.presentation.screens.main.components.LocationPermissionRequestDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusXTheme(dynamicColor = false, darkTheme = false) {
                PermissionRequester()
                MainScreen()
            }
        }
    }

    @Composable
    private fun PermissionRequester() {
        var showPermissionDeclinedRationale by rememberSaveable { mutableStateOf(false) }
        var showRationale by rememberSaveable { mutableStateOf(false) }
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                it.forEach { (permission, isGranted) ->
                    if (!isGranted && RunUtils.locationPermissions.contains(permission)) {
                        showPermissionDeclinedRationale = true
                    }
                }
            }
        )
        if (showPermissionDeclinedRationale)
            LocationPermissionRequestDialog(
                onDismissClick = {
                    if (!hasLocationPermission())
                        finish()
                    else showPermissionDeclinedRationale = false
                },
                onOkClick = { openAppSetting() }
            )
        if (showRationale)
            LocationPermissionRequestDialog(
                onDismissClick = ::finish,
                onOkClick = {
                    showRationale = false
                    permissionLauncher.launch(RunUtils.allPermissions)
                }
            )
        LaunchedEffect(key1 = Unit) {
            when {
                hasAllPermission() -> return@LaunchedEffect
                RunUtils.locationPermissions.any { shouldShowRequestPermissionRationale(it) } -> showRationale =
                    true

                else -> permissionLauncher.launch(RunUtils.allPermissions)
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationUtils.LOCATION_ENABLE_REQUEST_CODE && resultCode != Activity.RESULT_OK) {
            Toast.makeText(
                this,
                "Please enable GPS to get proper running statistics.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
