package com.geosolution.geoapp.presentation.screens.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.geosolution.geoapp.core.location.LocationService
import com.geosolution.geoapp.presentation.common.connectivity.NetworkTracker
import com.geosolution.geoapp.presentation.screens.home.components.CurrentNumberOfTasks
import com.geosolution.geoapp.presentation.screens.home.components.NoAccessDisplay
import com.geosolution.geoapp.presentation.screens.home.components.TopBar
import com.geosolution.geoapp.presentation.screens.home.components.TopBarHomeScreen
import com.geosolution.geoapp.presentation.screens.home.components.UserLocation
import com.geosolution.geoapp.presentation.screens.home.components.UserName
import com.geosolution.geoapp.presentation.screens.home.components.WeatherInfo
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeViewModel
import com.geosolution.geoapp.presentation.screens.main.MainScreen
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen
import com.geosolution.geoapp.presentation.ui.theme.AppColor
import com.geosolution.geoapp.presentation.ui.theme.CampusXTheme
import com.geosolution.geoapp.presentation.ui.theme.PrimaryColor
import com.geosolution.geolocation.GeoLocation
import com.huawei.hms.framework.common.ContextCompat.startService


@Composable
fun HomeScreen(
//    networkState: () -> NetworkTracker.State,

) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

//    val network_state = networkState()


    BackHandler {
        (context as? Activity)?.finish()
    }

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
//    state: HomeState,
//    durationInMillis: Long,
//    deleteRun: (Run) -> Unit,
//    showRun: (Run) -> Unit,
//    dismissDialog: () -> Unit,
//    navigateToRunScreen: () -> Unit,
//    navigateToRunningHistoryScreen: () -> Unit,
//    navigateToRunStats: () -> Unit,
) {
    Column {
        TopBarHomeScreen(
            modifier = Modifier
                .zIndex(1f),
//            user = state,
//            weeklyGoalInKm = state.user.weeklyGoalInKM,
//            distanceCoveredInCurrentWeekInKm = state.distanceCoveredInKmInThisWeek,
//            onWeeklyGoalClick = navigateToRunStats
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
//            if (state.runList.isEmpty())
//                EmptyRunListView(
//                    modifier = Modifier
//                )
//            else
//                RecentRunList(
//                    runList = state.runList,
//                    onItemClick = showRun
//                )
        }
    }
//    state.currentRunInfo?.let {
//        RunInfoDialog(
//            run = it,
//            onDismiss = dismissDialog,
//            onDelete = deleteRun
//        )
//    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenComposable() {

    CampusXTheme(dynamicColor = false, darkTheme = false) {
        HomeScreen()
    }

}