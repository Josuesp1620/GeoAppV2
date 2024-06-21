package com.geosolution.geoapp.presentation.screens.auth.started

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.screens.auth.started.components.StartedPageContent
import com.geosolution.geoapp.presentation.screens.auth.started.components.StartedPageHeader
import com.geosolution.geoapp.presentation.screens.auth.started.viewmodel.StartedPageViewModel
import com.geosolution.geoapp.presentation.screens.main.viewmodel.AuthState
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.collectWithLifecycle

@Composable
fun StartedPageScreen(
    authState: AuthState,
    navController: NavController,
    viewModel: StartedPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    BackHandler {
        (context as? Activity)?.finish()
    }

    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            StartedPageHeader()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp)
                    .size(400.dp),
                painter = painterResource(id = R.drawable.undraw_post_online),
                contentDescription = "undraw_post_online"
            )

            StartedPageContent(
                authState = authState,
                navigateToSigInScreen = {
                    viewModel.goToSigInScreen()
                },
                navigateToHomeScreen =
                {
                    viewModel.goToHomeScreen()
                },
            )
        }
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.NavigateTo -> {
                navController.navigate(it.screen)
            }
        }
    }
}