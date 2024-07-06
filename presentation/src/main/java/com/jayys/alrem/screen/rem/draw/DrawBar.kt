package com.jayys.alrem.screen.rem.draw

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DrawBar(latestSleepingTime: List<Long>, maxChartHeight: Dp, isRemPage: Boolean)
{
    val timeInDecimals = latestSleepingTime.map { seconds ->
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val decimalTime = hours + minutes / 100.0
        String.format("%.2f", decimalTime).toDouble()
    }.reversed()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom)
    {
        timeInDecimals.forEach{ barData ->
            var resultHeight by remember { mutableStateOf(0.dp) }

            LaunchedEffect(isRemPage) {
                resultHeight = if (isRemPage) { maxChartHeight * barData.toFloat() / 15 }
                else { 0.dp }
            }

            val animatedHeight by animateDpAsState(
                targetValue = resultHeight,
                animationSpec = tween(durationMillis = 1500, easing = FastOutLinearInEasing),
                label = ""
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                Box(
                    modifier = Modifier
                        .height(animatedHeight)
                        .width(20.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFB98FFE),
                                    Color(0xFF8FC9FE)
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)))
            }
        }
    }
}