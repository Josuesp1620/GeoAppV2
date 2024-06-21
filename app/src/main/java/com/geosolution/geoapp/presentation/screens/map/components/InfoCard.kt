package com.geosolution.geoapp.presentation.screens.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.R

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardColors(containerColor = Color.White, contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.Black),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 20.0.dp,
            topEnd = 20.0.dp,
            bottomEnd = 0.0.dp,
            bottomStart = 0.0.dp
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),
            onClick = { /*TODO*/ }) {
            Icon(
                Icons.Default.ArrowDropUp,
                contentDescription = "arrow-drop-up",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        HeaderInfoCard(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 25.dp
                )
                .fillMaxWidth(),
        )

//        Row(
//            horizontalArrangement = Arrangement.SpaceAround,
//            modifier = Modifier
//                .padding(horizontal = 20.dp)
//                .padding(bottom = 20.dp)
//                .height(IntrinsicSize.Min)
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp, vertical = 4.dp)
//
//        ) {
//            ItemInfoCard(
//                modifier = Modifier,
//                painter = painterResource(id = R.drawable.running_boy),
//                unit = "km",
//                value = "Cliente"
//            )
//            Box(
//                modifier = Modifier
//                    .width(1.dp)
//                    .fillMaxHeight()
//                    .padding(vertical = 8.dp)
//                    .align(Alignment.CenterVertically)
//                    .background(
//                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
//                    )
//            )
//            ItemInfoCard(
//                modifier = Modifier,
//                painter = painterResource(id = R.drawable.fire),
//                unit = "km",
//                value = "Text"
//            )
//            Box(
//                modifier = Modifier
//                    .width(1.dp)
//                    .fillMaxHeight()
//                    .padding(vertical = 8.dp)
//                    .align(Alignment.CenterVertically)
//                    .background(
//                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
//                    )
//            )
//            ItemInfoCard(
//                modifier = Modifier,
//                painter = painterResource(id = R.drawable.bolt),
//                unit = "km",
//                value = "Text"
//            )
//        }

    }

}

@Composable
fun ItemInfoCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    unit: String,
    value: String
) {
    Row(modifier = modifier.padding(4.dp)) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(20.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = unit,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun HeaderInfoCard(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Nombre:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Cliente 1",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }
        IconButton(
            onClick = {  },
            modifier = Modifier
                .size(60.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.error,
//                    shape = MaterialTheme.shapes.medium
//                )
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_info
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.size(16.dp))

        IconButton(
            onClick = {},
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_place
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HeaderInfoCardComposable() {
    InfoCard()
}