package com.jayys.alrem.screen.rem

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.utils.getRawResourceUri
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.utils.setAlarm
import com.jayys.alrem.utils.twelveHoursTimeFormatter
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel
import java.util.Date

@Composable
fun CalculateRemLayout(
    screenHeight: Dp,
    selectedHour: Int,
    selectedMin: Int,
    itemValue: String,
    lifecycleOwner: LifecycleOwner,
    onNavigateToMainScreen: () -> Unit,
    alarmDataViewModel: AlarmDataViewModel,
    switchViewModel: SwitchViewModel = hiltViewModel()
)
{
    val maxId by alarmDataViewModel.maxId.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)

    val context = LocalContext.current

    val selectedTime = twelveHoursTimeFormatter(selectedHour)
    val titleLine1 = if (itemValue == "sun") "일어나고 싶은 시간 : ${selectedTime.first} ${selectedTime.second}시 ${selectedMin}분\n"
    else "잠자고 싶은 시간 : ${selectedTime.first} ${selectedTime.second}시 ${selectedMin}분\n"

    val titleLine2 = if (itemValue == "sun") "잠들고 싶은 시간을 골라주세요"
    else "일어나고 싶은 시간을 골라주세요"

    val standardTime = listOf(
        Pair(4, 45),
        Pair(6, 15),
        Pair(7, 45),
        Pair(9, 15),
        Pair(10, 45),
        Pair(12, 15)
    )

    val calculatedTimes = if (itemValue == "sun") { standardTime.map { (h, m) -> subtractTime(selectedHour, selectedMin, h, m) } }
    else { standardTime.map { (h, m) -> addTime(selectedHour, selectedMin, h, m) } }

    val combinedTimes = calculatedTimes.zip(standardTime)

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(screenHeight * 0.12f))
            Text(
                text = titleLine1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Text(
                text = titleLine2,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.1f))

            LazyColumn(modifier = Modifier.height(screenHeight * 0.5f).fillMaxWidth()) {
                items(combinedTimes) { (calculated, standard) ->
                    val (calculatedH, calculatedM, day) = calculated
                    val (standardH, standardM) = standard

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.1f)
                        .clickable
                        {
                            val timeList =
                                listOf(selectedHour, selectedMin, calculatedH, calculatedM)
                            if (itemValue == "sun") {
                                sunAddAlarm(
                                    alarmDataViewModel,
                                    switchViewModel,
                                    maxId,
                                    timeList,
                                    context
                                )
                            } else {
                                moonAddAlarm(
                                    alarmDataViewModel,
                                    switchViewModel,
                                    maxId,
                                    timeList,
                                    context
                                )
                            }

                            Handler(Looper.getMainLooper()).postDelayed({
                                onNavigateToMainScreen()
                            }, 200)
                        },
                        contentAlignment = Alignment.Center)
                    {
                        val setTime = twelveHoursTimeFormatter(calculatedH)
                        Text(
                            text = String.format("%s %s %02d시 %02d분  (%02d : %02d 수면)", day, setTime.first, setTime.second, calculatedM, standardH, standardM),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 20.sp
                        )
                    }
                }
            }

        }
    }
}


fun subtractTime(hour: Int, min: Int, subHour: Int, subMin: Int): Triple<Int, Int, String> {
    var totalMinutes = hour * 60 + min
    val subTotalMinutes = subHour * 60 + subMin
    var day = "같은 날  "

    totalMinutes -= subTotalMinutes

    if (totalMinutes < 0) {
        totalMinutes += 24 * 60
        day = "전날  "
    }

    val newHour = totalMinutes / 60
    val newMin = totalMinutes % 60

    return Triple(newHour, newMin, day)
}


fun addTime(hour: Int, min: Int, addHour: Int, addMin: Int): Triple<Int, Int, String> {
    var totalMinutes = hour * 60 + min
    val addTotalMinutes = addHour * 60 + addMin
    var day = "같은 날  "

    totalMinutes += addTotalMinutes

    if (totalMinutes >= 24 * 60) {
        totalMinutes -= 24 * 60
        day = "다음 날  "
    }

    val newHour = totalMinutes / 60
    val newMin = totalMinutes % 60

    return Triple(newHour, newMin, day)
}




fun sunAddAlarm(
    alarmDataViewModel: AlarmDataViewModel,
    switchViewModel: SwitchViewModel,
    maxId: Int,
    timeList: List<Int>,
    context: Context
)
{
    val alarm = AlarmEntity(
        id = maxId+1,
        pageNum = 0,
        title = "수면 시간 : ${timeList[2]}시 ${timeList[3]}분",
        alarmDate = createDates(timeList[0], timeList[1]),
        alarmDayOfWeek = listOf(false, false, false, false, false, false, false),
        bellName = "dawn",
        bellRingtone = getRawResourceUri(context),
        bell = true,
        bellVolume = 5,
        vibration = true,
        tts = false,
        ttsVolume = 3,
        repeat = false,
        repeatMinute = 5,
        rem = true
    )

    alarmDataViewModel.addAlarm(alarm)

    switchViewModel.saveSwitchState(maxId+1, true)

    setGoToBedTime(context, timeList[2], timeList[3])

    setAlarm(alarm, context, false)

}

fun moonAddAlarm(
    alarmDataViewModel: AlarmDataViewModel,
    switchViewModel: SwitchViewModel,
    maxId: Int,
    timeList: List<Int>,
    context: Context
)
{
    val alarm = AlarmEntity(
        id = maxId+1,
        pageNum = 0,
        title = "수면 시간 : ${timeList[0]}시 ${timeList[1]}분",
        alarmDate = createDates(timeList[2], timeList[3]),
        alarmDayOfWeek = listOf(false, false, false, false, false, false, false),
        bellName = "dawn",
        bellRingtone = getRawResourceUri(context),
        bell = true,
        bellVolume = 5,
        vibration = true,
        tts = false,
        ttsVolume = 3,
        repeat = false,
        repeatMinute = 5,
        rem = true
    )

    alarmDataViewModel.addAlarm(alarm)

    switchViewModel.saveSwitchState(maxId+1, true)

    setGoToBedTime(context, timeList[0], timeList[1])

    setAlarm(alarm, context, false)
}


fun createDates(hour: Int, minute: Int): List<Date>
{
    val dayOfWeekMap = mapOf(
        "일" to Calendar.SUNDAY,
        "월" to Calendar.MONDAY,
        "화" to Calendar.TUESDAY,
        "수" to Calendar.WEDNESDAY,
        "목" to Calendar.THURSDAY,
        "금" to Calendar.FRIDAY,
        "토" to Calendar.SATURDAY
    )

    val dates = dayOfWeekMap.values.map { dayOfWeek ->
        Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, dayOfWeek)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    return dates
}


fun setGoToBedTime(context: Context, hour: Int, min: Int)
{
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val now = Calendar.getInstance()
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, min)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    if (calendar.before(now)) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", 0)
        putExtra("action", "goToBedTime")
    }

    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent)
            }
        } else {
            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent)
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }

}