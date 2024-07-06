package com.jayys.alrem.screen.rem


import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import kotlin.random.Random


@Composable
fun RemAnalysisLayout(average: Long, showText: Boolean)
{
    val context = LocalContext.current


    val hours = average / 3600
    val minutes = (average % 3600) / 60
    val timeText = buildString {
        if (hours > 0) {
            append("${hours}시간 ")
        }
        if (minutes > 0) {
            append("${minutes}분 ")
        }
        if(hours <= 0 && minutes <= 0)
        {
            append("없어요")
        }
    }.trim()

    val displayText = if (hours <= 0 && minutes <= 0) {
        "이번 달 평균 수면 시간은 $timeText."
    } else {
        "이번 달 평균 수면 시간은 $timeText 입니다."
    }

    val solutionText = if (hours <= 0 && minutes <= 0) solutionToSleep(context, "없음")
    else if( hours < 7) solutionToSleep(context, "부족")
    else if (hours < 9) solutionToSleep(context, "적절")
    else solutionToSleep(context, "과다")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = showText,
            enter = fadeIn(animationSpec = tween(durationMillis = 1500))
        ){
            Text(text = displayText,
                fontSize = 16.sp,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = showText,
            enter = fadeIn(animationSpec = tween(durationMillis = 3500)) + slideInVertically(
                animationSpec = tween(durationMillis = 3500),
                initialOffsetY = { it }
            )
        ) {
            Text(
                text = solutionText,
                fontSize = 16.sp,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

    }

}

fun solutionToSleep(context: Context, level: String): String {

    val range = when(level)
    {
        "없음" -> (100..101)

        "부족" -> (0..5)

        "적절" -> (5..9)

        "과다" -> (9..12)

        else -> (100..101)
    }


    val textResId = when (range.random()) {
        0 -> R.string.solution0     1 -> R.string.solution1
        2 -> R.string.solution2     3 -> R.string.solution3
        4 -> R.string.solution4     5 -> R.string.solution5
        6 -> R.string.solution6     7 -> R.string.solution7
        8 -> R.string.solution8     9 -> R.string.solution9
        10 -> R.string.solution10    11 -> R.string.solution11
        else -> R.string.solution100
    }

    return context.getString(textResId)

}
