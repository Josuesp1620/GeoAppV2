package com.geosolution.geoapp.presentation.ui.utils

import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val radius = 16f // Radio de las esquinas redondeadas para la parte superior

        // Esquina superior izquierda
        path.addRoundRect(
            RoundRect(0f, 0f, size.width, size.height)
//            RoundRect(0f, 0f, size.width, size.height),
//            floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f),
//            android.graphics.Path.Direction.CW
        )

        return Outline.Generic(path)
    }
}