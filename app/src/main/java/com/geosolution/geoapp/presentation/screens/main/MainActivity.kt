package com.geosolution.geoapp.presentation.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.presentation.screens.auth.started.StartedPageScreen
import com.geosolution.geoapp.presentation.screens.navigations.Navigation
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusXTheme(dynamicColor = false, darkTheme = false) {
                Navigation()
            }
        }
    }
}
