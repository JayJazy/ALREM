package com.jayys.alrem.screen.alarmadd

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.component.ToggleButton
import com.jayys.alrem.viemodel.SettingDataViewModel

@Composable
fun DayOfWeekLayout(screenHeight: Dp, settingDataViewModel: SettingDataViewModel)
{
    val weekdays = listOf("일", "월", "화", "수", "목", "금", "토")
    val toggleStates = remember { mutableStateListOf(*settingDataViewModel.dayOfWeekList.toTypedArray()) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.08f)
        .padding(horizontal = 8.dp))
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            weekdays.forEachIndexed { index, day ->
                val modifier = Modifier.padding(horizontal = 4.dp)
                val buttonColor = if (toggleStates[index]) MaterialTheme.colorScheme.onPrimary else Color(0xFF4D4D4D)
                val textColor = when(index)
                {
                    0 -> Color.Red
                    6 -> Color.Blue
                    else -> Color.Black
                }
                ToggleButton(
                    day = day,
                    color = buttonColor,
                    textColor = textColor,
                    onClick =
                    {
                        toggleStates[index] = !toggleStates[index]
                        settingDataViewModel.dayOfWeekList[index] = toggleStates[index]
                    },
                    modifier = modifier.size(35.dp)
                )
            }
        }
    }
}