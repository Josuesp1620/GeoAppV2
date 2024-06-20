package com.geosolution.geoapp.presentation.screens.auth.started.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.ui.widgets.TextCustom

@Preview(showSystemUi = true)
@Composable
fun StartedPageHeader() {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_geoapp),
            contentDescription = "logo-black",
            modifier = Modifier.size(20.dp).padding(end = 1.dp)
        )

        TextCustom(
            text = "Geo",
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.secondary
        )

        TextCustom(
            text= "App",
            fontWeight = FontWeight(500)
        )
    }
}