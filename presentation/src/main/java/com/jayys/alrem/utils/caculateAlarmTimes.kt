package com.jayys.alrem.utils

import android.content.Context
import android.widget.Toast
import com.jayys.alrem.viemodel.SettingDataViewModel
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


fun calculateAlarmTimes(
    context: Context,
    selectedDays: List<String>,
    allDaysSelected: Boolean,
    selectedDates: List<Date>,
    dayOfWeekMap: Map<String, Int>,
    settingDataViewModel: SettingDataViewModel
)
{

    val now = Calendar.getInstance()
    val currentTime = now.timeInMillis

    if (selectedDays.isEmpty() || allDaysSelected) {
        // 설정한 시간을 오늘 날짜 기준으로 생성
        val settingTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, settingDataViewModel.hour)
            set(Calendar.MINUTE, settingDataViewModel.min)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // 만약 설정 시간이 현재 시간보다 이전이라면 다음 날로 설정
            if (timeInMillis <= currentTime) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val diffInMillis = settingTime.timeInMillis - currentTime
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

        val (adjustedHours, adjustedMinutes) = adjustTime(diffInHours, diffInMinutes)

        Toast.makeText(context, "${formatTimeDifference(hours = adjustedHours, minutes = adjustedMinutes)} 후에 알람이 울려요", Toast.LENGTH_LONG).show()
    }
    else
    {
        // 기존의 선택된 요일에 대한 계산 로직
        val nextSelectedDate = selectedDates.filter { it.time > currentTime }.minByOrNull { it.time }

        if (nextSelectedDate != null) {
            val diffInMillis = nextSelectedDate.time - currentTime
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
            val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24
            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

            val (adjustedHours, adjustedMinutes) = adjustTime(diffInHours, diffInMinutes)

            Toast.makeText(context, "${formatTimeDifference(diffInDays, adjustedHours, adjustedMinutes)} 후에 알람이 울려요", Toast.LENGTH_LONG).show()

            // 현재 요일이 선택된 요일 중 하나인지 확인
            val currentDayOfWeek = now.get(Calendar.DAY_OF_WEEK)
            val currentDaySelected = selectedDays.any { dayOfWeekMap[it] == currentDayOfWeek }

            if (currentDaySelected) {
                val selectedTimeToday = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, settingDataViewModel.hour)
                    set(Calendar.MINUTE, settingDataViewModel.min)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                if (now.before(selectedTimeToday)) {
                    val timeDiffMillis = selectedTimeToday.timeInMillis - currentTime
                    val timeDiffHours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis)
                    val timeDiffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis) % 60

                    val (adjustedHour, adjustedMinute) = adjustTime(timeDiffHours, timeDiffMinutes)

                    Toast.makeText(context, "${formatTimeDifference(hours = adjustedHour, minutes = adjustedMinute)} 후에 알람이 울려요", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}



fun adjustTime(hours: Long, minutes: Long): Pair<Long, Long> {
    var adjustedHours = hours
    var adjustedMinutes = minutes + 1 // 분에 1을 더함

    if (adjustedMinutes >= 60) {
        adjustedHours += 1
        adjustedMinutes = 0
    }

    return Pair(adjustedHours, adjustedMinutes)
}

fun formatTimeDifference(days: Long = 0, hours: Long, minutes: Long): String {
    val parts = mutableListOf<String>()
    if (days > 0) parts.add("${days}일")
    if (hours > 0) parts.add("${hours}시간")
    if (minutes > 0) parts.add("${minutes}분")
    return if (parts.isEmpty()) "0분" else parts.joinToString(" ")
}