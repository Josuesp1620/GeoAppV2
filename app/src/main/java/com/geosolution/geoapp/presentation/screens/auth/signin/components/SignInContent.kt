package com.geosolution.geoapp.presentation.screens.auth.signin.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.presentation.screens.auth.signin.viewModel.SignInViewModel
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun SingInContent(
    viewModel: SignInViewModel
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("")  }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email-icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                email = it
            },
            label = { Text(text = "Correo electronico") },
            placeholder = { Text(text = "Correo electronico") },
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "lock-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                password= it
            },
            label = { Text(text = "Contraseña") },
            placeholder = { Text(text = "Contraseña") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .height(45.dp),
            onClick = {
                viewModel.signIn(email, password)
            },
            shape = ShapeDefaults.Small
        ) {
            TextCustom(
                text= "Iniciar Sessión",
                color = Color.White,
                fontWeight = FontWeight(500)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        TextCustom(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Olvide mi contraseña?",
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontSize = 17.0
        )
    }
}