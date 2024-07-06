package com.jayys.alrem.screen.main

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.component.AlarmListItem
import com.jayys.alrem.utils.getRawResourceUri
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel


@Composable
fun AlarmListLayout(
    screenHeight: Dp,
    alarmError: String?,
    switchError: String?,
    alarmList: List<AlarmEntity>,
    switchStates: Map<Int, Boolean>,
    settingDataViewModel: SettingDataViewModel,
    switchViewModel: SwitchViewModel,
    alarmDataViewModel: AlarmDataViewModel,
    onNavigateToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit
)
{
    val context = LocalContext.current
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.58f))
        {
            when {
                alarmError != null -> {
                    Text(text = "$alarmError\n앱을 다시 시작해 주세요", fontSize = 20.sp, color = Color.White)
                }
                switchError != null -> {
                    Text(text = "$switchError\n앱을 다시 시작해 주세요", fontSize = 20.sp, color = Color.White)
                }
                else -> {
                    LazyColumn {
                        items(alarmList) { alarm: AlarmEntity ->
                            val isChecked = switchStates[alarm.id] ?: true
                            AlarmListItem(screenHeight, alarm, isChecked, switchViewModel, alarmDataViewModel, onNavigateToAlarmAddScreen)
                        }
                    }
                }
            }
        }

        AddButton(
            modifier = Modifier,
            text = "알 람 추 가",
            screenHeight = screenHeight
        ) { onNavigateToAlarmAddScreen(dummyAlarmData(context), settingDataViewModel.createSettingData()) }

    }
}

fun dummyAlarmData(context: Context): AlarmEntity {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 7)
    calendar.set(Calendar.MINUTE, 30)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return AlarmEntity(
        id = 0,
        pageNum = 0,
        title = "",
        alarmDate = listOf(calendar.time),
        alarmDayOfWeek = listOf(false, false, false, false, false, false, false),
        bellName = "dawn",
        bellRingtone = getRawResourceUri(context),
        bell = true,
        bellVolume = 2,
        vibration = true,
        tts = false,
        ttsVolume = 2,
        repeat = false,
        repeatMinute = 5,
        rem = false
    )
}

