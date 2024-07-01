package com.geosolution.geoapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.geosolution.geoapp.domain.model.Client
import com.geosolution.geoapp.domain.model.User
import com.geosolution.geoapp.presentation.screens.home.components.CardItem
import com.geosolution.geoapp.presentation.screens.home.components.EmptyListView
import com.geosolution.geoapp.presentation.screens.home.components.TopBarHomeScreen
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeState
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeViewModel
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen

@Composable
fun HomeScreen(
    user: User?,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state by homeViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(NavScreen.CreateClientScreen.route)
                }
            ) {
                Icon(Icons.Filled.Add, null, tint = Color.White)
            }
        },
        containerColor = Color.White,
    ) { paddingValues ->

        HomeScreenContent(
            state = state,
            user = user,
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            activeLocation = { checked ->
                homeViewModel.activeLocation(checked)
            }
        )

        }
    }


@Composable
fun HomeScreenContent(
    state: HomeState,
    user: User?,
    activeLocation: (checked: Boolean) -> Unit,
    navController: NavController,
    modifier: Modifier,
    bottomPadding: Dp = 0.dp,
) {

    Column(
        modifier
    ) {
        user?.let {
            TopBarHomeScreen(
                state = state,
                modifier = Modifier
                    .zIndex(1f),
                activeLocation = { checked ->
                    activeLocation(checked)
                },
                user = user,
                navController = navController
            )
        }

        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 10.dp, horizontal = 24.dp)
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

            if(state.clientList.isEmpty()){
                EmptyListView()
            }else {
                ClientList(clientList = state.clientList, onItemClick = {})
            }

        }
    }
}

@Preview
@Composable
private fun ClientListComposable() {

    val list = listOf(
        Client(
            id=0,
            name="Josue",
            fullName = "Salazar",
            vat = "71858088",
            businessName = "JouCode",
            coordinates = "",
            address = "Av. Principal 123",
            image = ""
        ),
        Client(
            id=0,
            name="Josue",
            fullName = "Salazar",
            vat = "71858088",
            businessName = "JouCode",
            coordinates = "",
            address = "Av. Principal 123",
            image = ""
        )
    )

    ClientList(clientList = list) {

    }

}

@Composable
private fun ClientList(
    modifier: Modifier = Modifier,
    clientList: List<Client>,
    onItemClick: (Client) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp)
            .wrapContentHeight()
    ) {
        Column {
            clientList.forEachIndexed { i, client ->
                Column{
                    CardItem(
                        client = client,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable { onItemClick(client) }
                    )
                    if (i < clientList.lastIndex)
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .width(200.dp)
                                .padding(10.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                }
            }
        }

    }
}