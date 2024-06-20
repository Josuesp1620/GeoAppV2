package com.geosolution.geoapp.presentation.screens.auth.signin

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
import com.geosolution.geoapp.presentation.screens.auth.signin.components.SingInContent
import com.geosolution.geoapp.presentation.screens.auth.signin.viewModel.SignInViewModel
import com.geosolution.geoapp.presentation.ui.utils.event.Event
import com.geosolution.geoapp.presentation.ui.utils.event.collectWithLifecycle
import com.geosolution.geoapp.presentation.ui.widgets.HeaderBack
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
){
    SignInScreen(
        viewModel = viewModel,
        popBackStack = {
            if(navController.currentBackStackEntry?.destination?.route != "splash_screen") {
                navController.popBackStack()
            }
        }
    )

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.NavigateTo -> {
                navController.navigate(it.screen){
//                    popUpTo(SignInScreen())
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
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
                    .padding(horizontal = 20.dp)
                    .padding(paddingValues = paddingValues)
            ) {

                TextCustom(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Bienvenido de nuevo!",
                    letterSpacing = 2.0,
                    fontSize = 30.0,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.primary
                )

                TextCustom(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Inicia Sessión para continuar.",
                    letterSpacing = 1.0,
                    fontSize = 18.0,
                    fontWeight = FontWeight(400),
                )

                Spacer(modifier = Modifier.height(60.dp))

                AnimatedContent(viewModel.state, label = "") { state ->
                    if(state.loading){
                        Box(modifier = Modifier.fillMaxSize()
                            .padding(paddingValues = paddingValues)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    else{
                        SingInContent(viewModel = viewModel)
                    }
                }


            }
        }
    )

}