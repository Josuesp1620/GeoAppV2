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
    var fullName by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("")  }
    var vat by rememberSaveable { mutableStateOf("") }
    var businessName by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var coordinates by rememberSaveable { mutableStateOf("") }

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
            value = fullName,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "face-icon")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                fullName = it
            },
            label = { Text(text = "Apellidos") },
            placeholder = { Text(text = "Apellidos") },
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = vat,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email-icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                vat = it
            },
            label = { Text(text = "DNI / RUC") },
            placeholder = { Text(text = "DNI / RUC") },
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = businessName,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email-icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                businessName = it
            },
            label = { Text(text = "Nombre Comercial") },
            placeholder = { Text(text = "Nombre Comercial") },
        )


        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = address,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email-icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                address = it
            },
            label = { Text(text = "Dirección") },
            placeholder = { Text(text = "Dirección") },
        )


        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = coordinates,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email-icon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                coordinates = it
            },
            label = { Text(text = "Coordenadas") },
            placeholder = { Text(text = "LAT - LONG") },
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .height(45.dp),
            onClick = {
                      viewModel.create(name, fullName, vat, businessName, address, coordinates)
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