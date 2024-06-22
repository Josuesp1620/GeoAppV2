package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geosolution.geoapp.R
import com.geosolution.geoapp.presentation.screens.home.viewmodel.HomeViewModel
import com.geosolution.geoapp.presentation.screens.navigations.NavScreen


@Composable
fun TopBarHomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
//    user: User,
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .offset(y = (-24).dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
        )
        Column(modifier = modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.size(24.dp))
            TopBarProfile(
                navController = navController,
                modifier = Modifier.background(color = Color.Transparent),
//                user = user
            )
            Spacer(modifier = Modifier.size(32.dp))
            LocationInfoCard(homeViewModel)
        }
    }

}


@Composable
private fun TopBarProfile(
    navController: NavController,
    modifier: Modifier = Modifier,
//    user: User
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        UserProfilePic(
//            imgUri = user.image,
//            gender = user.gender,
//            modifier = Modifier
//                .size(40.dp)
//                .clip(CircleShape)
//        )
        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = buildAnnotatedString {
                append("Hola, ")
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.SemiBold),
                ) {
                    append("Josue")
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .weight(1f)
        )

        IconButton(
            onClick = {
                      navController.navigate(NavScreen.MapScreen.route)
            },
            modifier = Modifier
                .size(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_map),
                contentDescription = "Map",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}