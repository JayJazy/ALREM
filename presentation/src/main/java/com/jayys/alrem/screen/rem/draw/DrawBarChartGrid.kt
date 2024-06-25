package com.jayys.alrem.screen.rem.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DrawBarChartGrid(maxChartHeight: Dp)
{
    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxDrawHeight = maxChartHeight.toPx()
        val step = maxDrawHeight / 15
        val lineColor = Color(0xFF7ADFFF).copy(alpha = 0.5f)
        val dashPathEffect =  PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        // 수평 격자
        for (i in 0..15) {
            val currentPathEffect = if (i == 0) null else dashPathEffect
            if( i % 4 == 0 ){
                val y = size.height - (i * step)
                drawLine(
                    color = lineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = currentPathEffect
                )
            }
        }

        // y축 세로선
        drawLine(
            color = lineColor,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 1.dp.toPx()
        )
    }
}