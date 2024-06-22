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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
@Preview(showSystemUi = true)
fun LocationInfoCardComposable(
    modifier: Modifier = Modifier,
) {
    LocationInfoCard()
}
@Composable
fun LocationInfoCard(
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var isLocationCurrent by rememberSaveable { mutableStateOf(state.isLocationCurrent) }


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
                    checked = isLocationCurrent,
                    onCheckedChange = { checked ->
                        isLocationCurrent = checked
                        viewModel.updateLocationCurrentState(checked)
                        context.startService(
                            Intent(context, LocationService::class.java).apply {
                                action = if (checked) LocationService.ACTION_START else LocationService.ACTION_STOP
                            }
                        )
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