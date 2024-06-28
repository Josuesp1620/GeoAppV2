package com.geosolution.geoapp.presentation.screens.auth.started.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.presentation.screens.main.viewmodel.AuthState
import com.geosolution.geoapp.presentation.ui.widgets.LoadingPage
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun StartedPageContent(
    authState: AuthState,
    navigateToSigInScreen : () -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSigUScreen: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = 30.dp)
    ) {
        TextCustom(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Bienvenido !",
            letterSpacing = 2.0,
            fontSize = 30.0,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.primary
        )

        when (authState) {
            AuthState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            AuthState.Unauthenticated -> {
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .height(45.dp),
                    onClick = {
                        navigateToSigInScreen()
                    },
                    shape = ShapeDefaults.Small
                ) {
                    TextCustom(
                        text= "Iniciar Sessión",
                        color = Color.White,
                        fontWeight = FontWeight(500)
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .height(45.dp),
                    onClick = {
                        navigateToSigUScreen()
                    },
                    shape = ShapeDefaults.Small
                ) {
                    TextCustom(
                        text= "Registrarse",
                        color = Color.White,
                        fontWeight = FontWeight(500)
                    )
                }
            }
            is AuthState.Authenticated -> {
                navigateToHomeScreen()
            }
        }
    }
}