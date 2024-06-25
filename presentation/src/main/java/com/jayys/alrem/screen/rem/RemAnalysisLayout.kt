package com.jayys.alrem.screen.rem


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


@Composable
fun RemAnalysisLayout(average: Double, showText: Boolean)
{
    val hours = average.toInt()
    val minutes = ((average - hours) * 100).roundToInt()
    val timeText = buildString {
        if (hours > 0) {
            append("${hours}시간 ")
        }
        if (minutes > 0) {
            append("${minutes}분 ")
        }
    }.trim()

    AnimatedVisibility(
        visible = showText,
        enter = fadeIn(animationSpec = tween(durationMillis = 3000))
    ){
        Text(text = "이번 달 평균 수면 시간은 : $timeText 입니다.",
            fontSize = 16.sp,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp))
    }
}