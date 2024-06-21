package com.geosolution.geoapp.presentation.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geosolution.geoapp.presentation.screens.main.viewmodel.MainViewModel
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    BackHandler {
        (context as? Activity)?.finish()
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                contentAlignment = Alignment.Center
            ){
                TextCustom(
                    modifier = Modifier.fillMaxWidth(),
                    text = "HOME",
                    textAlign = TextAlign.Center
                )

            }
        }
    )


}