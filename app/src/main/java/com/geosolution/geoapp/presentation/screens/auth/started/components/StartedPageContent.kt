package com.geosolution.geoapp.presentation.screens.auth.started.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun StartedPageContent(
    navigateToSigInScreen : () -> Unit,
    navigateToSigUpScreen : () -> Unit,
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

        TextCustom(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 5.dp),
            text = "Gestiona tareas, clientes y ubicaciones, todo desde un solo lugar.",
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontSize = 17.0
        )

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

//        Button(
//            modifier = Modifier.fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//                .padding(top = 20.dp)
//                .height(45.dp),
//            onClick = {
//                navigateToSigUpScreen()
//            },
//            shape = ShapeDefaults.Small,
//            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
//            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Unspecified)
//        ) {
//            TextCustom(
//                text= "Registrate",
//                color = MaterialTheme.colorScheme.primary,
//                fontWeight = FontWeight(500)
//            )
//        }
    }
}