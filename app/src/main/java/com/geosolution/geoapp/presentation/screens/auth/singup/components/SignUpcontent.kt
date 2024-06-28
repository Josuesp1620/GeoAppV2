package com.geosolution.geoapp.presentation.screens.auth.singup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveRedEye
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
import com.geosolution.geoapp.presentation.screens.auth.singup.viewmodel.SignUpViewModel
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun SignUpContent(
    viewModel: SignUpViewModel
) {
    var fullname by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("")  }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("")  }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "account-box-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                name = it
            },
            label = { Text(text = "Nombres") },
            placeholder = { Text(text = "Nombres") },
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullname,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "face-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                fullname = it
            },
            label = { Text(text = "Apellidos") },
            placeholder = { Text(text = "Apellidos") },
        )

        Spacer(modifier = Modifier.height(25.dp))

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

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "lock-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                password = it
            },
            label = { Text(text = "Contraseña") },
            placeholder = { Text(text = "Contraseña") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) {
                        Icon(
                            imageVector = Icons.Default.RemoveRedEye,
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

        TextCustom(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Al registrarte, aceptas los terminos de servicio y las politicas de privacidad.",
            textAlign = TextAlign.Start,
            maxLines = 2,
            fontSize = 13.0
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .height(45.dp),
            enabled = email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && fullname.isNotBlank(),
            onClick = {
                if(email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && fullname.isNotBlank()){
                    viewModel.signUp(name, fullname, email, password)
                }
            },
            shape = ShapeDefaults.Small
        ) {
            TextCustom(
                text= "Registrarme",
                color = Color.White,
                fontWeight = FontWeight(500)
            )
        }
    }
}