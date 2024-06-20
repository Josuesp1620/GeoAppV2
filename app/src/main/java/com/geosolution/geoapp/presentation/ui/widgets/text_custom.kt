package com.geosolution.geoapp.presentation.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TextCustom(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Double = 18.0,
    isTitle: Boolean = false,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: Double = 0.0,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontFamily = if (isTitle) MaterialTheme.typography.displayLarge.fontFamily else MaterialTheme.typography.bodyLarge.fontFamily,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = color,
            letterSpacing = letterSpacing.sp,
            textAlign = textAlign,
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview
@Composable
fun TextCustomPreview() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            TextCustom(text = "Normal Text")
            TextCustom(text = "Title Text", isTitle = true, fontSize = 24.0, fontWeight = FontWeight.Bold)
            TextCustom(text = "Custom Text", color = Color.Red, maxLines = 2, overflow = TextOverflow.Clip)
        }
    }
}
