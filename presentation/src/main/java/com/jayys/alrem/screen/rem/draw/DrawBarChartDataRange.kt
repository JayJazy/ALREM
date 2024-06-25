package com.jayys.alrem.screen.rem.draw

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DrawBarChartDataRange(maxChartHeight: Dp) {

    val density = LocalDensity.current

    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxDrawHeight = with(density) { maxChartHeight.toPx() }
        val step = maxDrawHeight / 15
        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = with(density) { 15.sp.toPx() }
            textAlign = Paint.Align.RIGHT
        }

        for (i in 0..15) {
            if (i % 4 == 0) {
                val y = size.height - (i * step)
                val text = "${i}h"
                val x = size.width - 4.dp.toPx()
                drawContext.canvas.nativeCanvas.drawText(text, x, y, textPaint)
            }
        }
    }
}