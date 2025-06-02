package com.geosolution.geoapp.presentation.ui.utils.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

data class PermissionState(
    val hasLocationPermission: Boolean,
    val hasNotificationPermission: Boolean, // True if API < 33 or permission granted
    val shouldShowRationaleMap: Map<String, Boolean> // Permission string to boolean
) {
    val allPermissionsGranted: Boolean
        get() = hasLocationPermission && hasNotificationPermission
}

@Composable
fun rememberPermissionRequestManager(
    onPermissionsResult: (Map<String, Boolean>) -> Unit
): Pair<PermissionState, ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>> {
    val context = LocalContext.current // If LocalContext is not available here, this utility might need to be adapted or context passed in. Assuming it's available in this composable util's scope.
                                     // For a non-composable util, context would be a parameter.
                                     // Given it's a `remember` function, it's for composable scope.

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

    var permissionState by remember {
        mutableStateOf(
            PermissionState(
                hasLocationPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED,
                hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else true, // Notifications permission not needed before Android 13
                shouldShowRationaleMap = emptyMap()
            )
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsResultMap ->
            val newLocationPermission = permissionsResultMap[Manifest.permission.ACCESS_FINE_LOCATION] ?: permissionState.hasLocationPermission
            val newNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsResultMap[Manifest.permission.POST_NOTIFICATIONS] ?: permissionState.hasNotificationPermission
            } else true

            // For shouldShowRationale, it's complex with the new ActivityResultContracts.
            // An Activity instance is needed for `shouldShowRequestPermissionRationale`.
            // This composable utility focuses on launching and reporting results.
            // Rationale display should typically be handled by the calling Composable before launching.
            // For simplicity, this version doesn't populate shouldShowRationaleMap from here.
            // It could be enhanced if direct rationale check from here is needed, possibly by passing an Activity.

            permissionState = PermissionState(
                hasLocationPermission = newLocationPermission,
                hasNotificationPermission = newNotificationPermission,
                shouldShowRationaleMap = emptyMap() // Simplified: rationale to be handled by caller
            )
            onPermissionsResult(permissionsResultMap)
        }
    )

    // Effect to update permission state if it changes externally (e.g. via app settings)
    // This is a basic check on recomposition. For more robust live updates, a lifecycle observer might be needed.
    LaunchedEffect(Unit) { // Re-check when composable enters composition / context changes
        permissionState = PermissionState(
            hasLocationPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED,
            hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true,
            shouldShowRationaleMap = emptyMap()
        )
    }


    return Pair(permissionState, launcher)
}

// Helper function to check all permissions (can be called from non-composable scope if context is available)
fun checkAllPermissions(context: Context): PermissionState {
    return PermissionState(
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED,
        hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true,
        shouldShowRationaleMap = emptyMap() // Rationale check needs an Activity
    )
}
