package com.geosolution.geoapp.presentation.screens.home.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geosolution.geoapp.core.location.LocationService
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeViewModel
import com.geosolution.geolocation.GeoLocation

@Composable
@Preview(showSystemUi = true)
fun WeeklyGoalCardComposable(
    modifier: Modifier = Modifier,
) {
    WeeklyGoalCard()
}
@Composable
fun WeeklyGoalCard(
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ElevatedCard(
        modifier = modifier
        .fillMaxWidth(),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end=24.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = "Pueblo Libre, Lima - Perú",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Switch(
                    checked = state.isLocationCurrent,
                    onCheckedChange = { checked ->
                        if (checked) {
                            val intent = Intent(context, LocationService::class.java)
                            context.startService(intent)
                        } else {
                            GeoLocation.stopLocationUpdates()
                        }
                        viewModel.updateLocationCurrentState(checked)
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Latitud: -12.061901\nLongitud: -77.057229",
                    modifier = Modifier
                        .weight(1f),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Bearing: 65°",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            if(state.isLocationCurrent){
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round,
                )
            }

        }
    }

}