package com.geosolution.geoapp.presentation.screens.client

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geosolution.geoapp.presentation.screens.auth.singup.components.SignUpContent
import com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel.SignUpViewModel
import com.geosolution.geoapp.presentation.screens.client.components.CreateClientContent
import com.geosolution.geoapp.presentation.screens.client.viewmodel.CreateClientViewModel
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.collectWithLifecycle
import com.geosolution.geoapp.presentation.ui.widgets.HeaderBack
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun CreateClientScreen(
    navController: NavController,
    viewModel: CreateClientViewModel = hiltViewModel()
){
    CreateClientScreen(
        viewModel = viewModel,
        popBackStack = {
            Log.d("BACK_APP", "BACK_APP")
            if(navController.currentBackStackEntry?.destination?.route != "splash_screen") {
                navController.popBackStack()
            }
        }
    )

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.NavigateTo -> {
                navController.navigate(it.screen){

                }
            }
        }
    }
}

@Composable
fun CreateClientScreen(
    viewModel: CreateClientViewModel,
    popBackStack: () -> Unit
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeaderBack { popBackStack() }
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 20.dp)
            ) {

                TextCustom(
                    text = "Cree una nuevo cliente.",
                    letterSpacing = 1.0,
                    fontSize = 18.0,
                    fontWeight = FontWeight(400),
                )

                Spacer(modifier = Modifier.height(30.dp))

                AnimatedContent(viewModel.state, label = "") { state ->
                    if(state.loading){
                        Box(modifier = Modifier.fillMaxSize()
                            .padding(paddingValues = paddingValues)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    else{
                        CreateClientContent(viewModel = viewModel)
                    }
                }

            }
        }
    )
}

