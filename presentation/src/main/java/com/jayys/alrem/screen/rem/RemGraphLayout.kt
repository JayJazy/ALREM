package com.jayys.alrem.screen.rem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.R
import com.jayys.alrem.screen.rem.draw.DrawGraphDataRange
import com.jayys.alrem.screen.rem.draw.drawGraph
import com.jayys.alrem.screen.rem.draw.DrawGraphGrid
import java.util.Calendar


@Composable
fun RemGraphLayout(screenHeight: Dp, showText: Boolean)
{
    val calendar = Calendar.getInstance()
    val initialYear = calendar.get(Calendar.YEAR)
    val initialMonth = calendar.get(Calendar.MONTH) + 1

    var average by remember { mutableDoubleStateOf(0.0) }
    var currentYear by remember { mutableIntStateOf(initialYear) }
    var currentMonth by remember { mutableIntStateOf(initialMonth) }

    fun updateMonth(increment: Boolean) {
        if (increment) {
            currentMonth++
            if (currentMonth > 12) {
                currentMonth = 1
                currentYear++
            }
        } else {
            currentMonth--
            if (currentMonth < 1) {
                currentMonth = 12
                currentYear--
            }
        }
    }


    Row(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.07f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start)
    {
        IconButton(onClick = { updateMonth(false) }) {
            Icon(painter = painterResource(id = R.drawable.previous_icon), contentDescription = "previous", tint = Color.White)
        }

        Text(text = " ${currentYear}년 ${currentMonth}월 ", color = Color.White)

        IconButton(onClick = { updateMonth(true) }) {
            Icon(painter = painterResource(id = R.drawable.next_icon), contentDescription = "next", tint = Color.White)
        }
    }

    Spacer(modifier = Modifier.height(screenHeight * 0.03f))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.25f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
        ) {
            DrawGraphDataRange()
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
        ) {
            DrawGraphGrid()
            average = drawGraph(constraints)
        }
    }

    Spacer(modifier = Modifier.height(screenHeight * 0.06f))

    Box(modifier = Modifier.fillMaxWidth().height(screenHeight * 0.25f), contentAlignment = Alignment.TopStart){
        RemAnalysisLayout(average = average, showText)
    }

}



