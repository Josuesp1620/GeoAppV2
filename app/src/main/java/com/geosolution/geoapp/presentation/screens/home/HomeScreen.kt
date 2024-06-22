package com.geosolution.geoapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.geosolution.geoapp.presentation.screens.home.components.TopBarHomeScreen
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme

@Composable
fun HomeScreen(
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                }
            ) {
                Icon(Icons.Filled.Add, null, tint = Color.White)
            }
        },
        containerColor = Color.White,
    ) { paddingValues ->

        HomeScreenContent(
            modifier = Modifier.padding(paddingValues)
        )

        }
    }


@Composable
fun HomeScreenContent(
    modifier: Modifier,
    bottomPadding: Dp = 0.dp,
) {
    Column(
        modifier
    ) {
        TopBarHomeScreen(
            modifier = Modifier
                .zIndex(1f),
        )

        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 28.dp, horizontal = 24.dp)
        ) {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "All",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = bottomPadding)
        ) {
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenComposable() {

    CampusXTheme(dynamicColor = false, darkTheme = false) {
        HomeScreen()
    }

}