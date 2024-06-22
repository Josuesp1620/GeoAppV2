package com.geosolution.geoapp.presentation.screens.client.components

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.presentation.screens.client.viewmodel.CreateClientViewModel
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Composable
fun CreateClientContent(
    viewModel: CreateClientViewModel
) {
    var fullname by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("")  }
    var email by rememberSaveable { mutableStateOf("") }

    Column(
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullname,
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "account-box-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                fullname = it
            },
            label = { Text(text = "Nombre Completo") },
            placeholder = { Text(text = "Nombre Completo") },
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "face-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                username = it
            },
            label = { Text(text = "Usuario") },
            placeholder = { Text(text = "Usuario") },
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .height(45.dp),
            onClick = {
                      viewModel.create(fullname, username, email)
            },
            shape = ShapeDefaults.Small
        ) {
            TextCustom(
                text= "Crear Cliente",
                color = Color.White,
                fontWeight = FontWeight(500)
            )
        }
    }
}