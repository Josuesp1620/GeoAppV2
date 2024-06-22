package com.geosolution.geoapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geosolution.geoapp.R

@Composable
@Preview(showBackground = true)
fun EmptyListView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp, vertical = 20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_info),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(16.dp))
        val inlineContentMap = mapOf(
            "run_icon_img" to InlineTextContent(
                placeholder = Placeholder(
                    MaterialTheme.typography.bodyLarge.fontSize,
                    MaterialTheme.typography.bodyLarge.fontSize,
                    PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
        )
        Text(
            text = buildAnnotatedString {
                append("Parece que no tenemos ningún registro. Grabe lo que ejecuta haciendo clic en el ")
                appendInlineContent(id = "run_icon_img")
                append(" botón")
            },
            inlineContent = inlineContentMap,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}