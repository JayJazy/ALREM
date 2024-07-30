package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.alarmadd.update.updateAlarmDataToSettingData
import com.jayys.alrem.utils.cancelExistingAlarm
import com.jayys.alrem.utils.setAlarm
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun AlarmListItem(
    screenHeight: Dp,
    alarm: AlarmEntity,
    isChecked: Boolean,
    switchViewModel: SwitchViewModel,
    alarmDataViewModel: AlarmDataViewModel,
    onNavigateToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit
)
{
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.2f)
        .padding(horizontal = 12.dp)
        .padding(vertical = 8.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colorScheme.primary)
        .clickable { onNavigateToAlarmAddScreen(alarm, updateAlarmDataToSettingData(alarm)) })
    {

        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ){
            Spacer(modifier = Modifier.height(screenHeight * 0.001f))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(0.7f)){
                    Column(modifier = Modifier
                        .padding(start = 15.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(text = getDays(alarm.alarmDate, alarm.alarmDayOfWeek),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 5.dp))

                        Spacer(modifier = Modifier.height(screenHeight * 0.007f))

                        Text(text = getTime(alarm.alarmDate),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 30.sp)
                    }
                }
                
                Box(modifier = Modifier.weight(0.3f), contentAlignment = Alignment.Center){
                    Switch(
                        checked = isChecked,
                        onCheckedChange = { newState ->
                            switchViewModel.saveSwitchState(alarm.id, newState)
                            if(newState)
                            {
                                setAlarm(alarm, context, true)
                            }
                            else
                            {
                                cancelExistingAlarm(alarm.id, context)
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = MaterialTheme.colorScheme.onSecondary,
                            uncheckedBorderColor = Color.Gray,
                            uncheckedTrackColor = Color.Transparent
                        )
                    )
                }
            }


            Box(modifier = Modifier
                .height(screenHeight * 0.25f * 0.2f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(start = 15.dp))
            {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(text = alarm.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f))

                    Box(modifier = Modifier
                        .weight(0.2f)){
                        IconButton(onClick = { expanded = true }) {
                            Icon(painter = painterResource(id = R.drawable.more_icon), contentDescription = "알람 더보기", tint = Color.White)
                        }

                        DropdownMenu(modifier = Modifier.wrapContentSize().background(MaterialTheme.colorScheme.primary),
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                onClick =
                                {
                                    cancelExistingAlarm(alarm.id, context)
                                    alarmDataViewModel.deleteAlarm(alarm.id)
                                    expanded = false
                                },
                                leadingIcon = { Icon(painterResource(id = R.drawable.delete_icon), contentDescription = "휴지통", tint = Color.White)},
                                text =
                                {
                                    Text(text = "알람 삭제  ", color = Color.White)
                                }
                            )
                        }
                    }

                }
            }

        }
    }
}



private fun getTime(alarmDate: List<Date>): String {

    val calendar = Calendar.getInstance().apply { time = alarmDate.first() }
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "오전" else "오후"
    val hourIn12Format = if (hour > 12) hour - 12 else hour
    return String.format("%s %02d:%02d", amPm, hourIn12Format, minute)

}


private fun getDays(alarmDate: List<Date>, alarmDayOfWeek: List<Boolean>): String {
    val dayOfWeekFormatter = SimpleDateFormat("E", Locale.KOREAN)
    val allDays = setOf("일", "월", "화", "수", "목", "금", "토")

    val emptyDay = allDays.filterIndexed { index, _ -> alarmDayOfWeek[index] }

    val selectedDaysFromDates = alarmDate.map { date ->
        Calendar.getInstance().apply { time = date }
        dayOfWeekFormatter.format(date)
    }.toSet()

    return if (emptyDay.isEmpty()){
        "하루"
    }
    else if (selectedDaysFromDates == allDays) {
        "매일"
    } else {
        selectedDaysFromDates.joinToString(separator = "  ")
    }


}