package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.R
import com.geosolution.geoapp.domain.model.Client


@Composable
@Preview(showSystemUi = true)
fun CardItemComposable() {

    val client = Client(
        id=0,
        name="Josue",
        fullName = "Salazar",
        vat = "71858088",
        businessName = "JouCode",
        coordinates = "",
        address = "Av. Principal 123",
        image = ""
    )

    CardItem(modifier = Modifier.padding(10.dp), client)
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    client: Client,
    showTrailingIcon: Boolean = true
) {
    Row(
        modifier = modifier
            .clip(ShapeDefaults.Medium)
            .background(color = MaterialTheme.colorScheme.primary.copy(
               alpha = 0.2f
            ))

            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp),
        Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_geoapp_white),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(16.dp))
        CardInfo(
            modifier = Modifier
                .weight(1f).fillMaxHeight(),
            client = client
        )
        if (showTrailingIcon)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "More info",
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.onSurface
            )
    }
}
