package com.geosolution.geoapp.presentation.screens.home.components

import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.geosolution.geoapp.R
import com.geosolution.geoapp.domain.model.Client
import org.maplibre.android.geometry.LatLng
import androidx.core.net.toUri


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
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp), content = function(client)
    )
}

@Composable
private fun function(client: Client): @Composable() (RowScope.() -> Unit) =
    {
        // Imagen del perfil
        Image(
            painter = painterResource(id = R.drawable.google_satelite),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Información del cliente
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = client.name!!,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Text(
                    text = "${client.vat}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${client.businessName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        OpenMapsIcon(
            modifier = Modifier.align(Alignment.CenterVertically),
            coordinates = client.coordinates!!,
            address = client.address!!,
        )
        Spacer(modifier = Modifier.width(8.dp))
        OpenGoogleMapsIcon(
            modifier = Modifier.align(Alignment.CenterVertically),
            coordinates = client.coordinates,
            address = client.address,
        )
    }

@Composable
fun OpenGoogleMapsIcon(
    modifier: Modifier = Modifier,
    coordinates: String = "0,0",
    address: String = "",
) {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Default.Api,
        contentDescription = "More info",
        modifier = modifier
            .size(24.dp)
            .clickable {
                val gmmIntentUri = "geo:${coordinates}?q=${address}".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(context, mapIntent, null)
            },
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun OpenMapsIcon(
    modifier: Modifier = Modifier,
    coordinates: String = "0,0",
    address: String = "",
) {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Default.Map,
        contentDescription = "More info",
        modifier = modifier
            .size(24.dp)
            .clickable {
                val gmmIntentUri = "geo:${coordinates}?q=${address}".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(context, mapIntent, null)
            },
        tint = MaterialTheme.colorScheme.primary
    )
}