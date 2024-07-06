package com.jayys.alrem.screen.alarmadd.update

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.viemodel.SettingDataViewModel
import java.util.Calendar


fun settingDataToUpdatedAlarmData(settingDataViewModel: SettingDataViewModel, id: Int) : AlarmEntity
{
    val weekdays = listOf("일", "월", "화", "수", "목", "금", "토")
    val selectedDays = weekdays.filterIndexed { index, _ -> settingDataViewModel.dayOfWeekList[index] }
    val dayOfWeekMap = mapOf(
        "일" to Calendar.SUNDAY,
        "월" to Calendar.MONDAY,
        "화" to Calendar.TUESDAY,
        "수" to Calendar.WEDNESDAY,
        "목" to Calendar.THURSDAY,
        "금" to Calendar.FRIDAY,
        "토" to Calendar.SATURDAY
    )

    val allDaysSelected = settingDataViewModel.dayOfWeekList.all { it }

    val selectedDates = if (selectedDays.isEmpty() || allDaysSelected) {
        dayOfWeekMap.values.map { dayOfWeek ->
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, dayOfWeek)
                set(Calendar.HOUR_OF_DAY, settingDataViewModel.hour)
                set(Calendar.MINUTE, settingDataViewModel.min)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
        }
    } else {
        selectedDays.map { day ->
            val today = Calendar.getInstance()
            val targetDay = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, settingDataViewModel.hour)
                set(Calendar.MINUTE, settingDataViewModel.min)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, dayOfWeekMap[day] ?: throw IllegalArgumentException("Invalid day of week: $day"))

                // 오늘 이후의 날짜로 설정
                if (before(today)) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }.time
            targetDay
        }
    }


    return AlarmEntity(
        id = id,
        pageNum = settingDataViewModel.pageNumber,
        title = settingDataViewModel.alarmName,
        alarmDate = selectedDates,
        alarmDayOfWeek = settingDataViewModel.dayOfWeekList,

        bellName = settingDataViewModel.bellName,
        bellRingtone = settingDataViewModel.ringtoneStringUri,
        bell = settingDataViewModel.switchState[0],
        bellVolume = settingDataViewModel.bellVolume,

        vibration = settingDataViewModel.switchState[1],

        tts = settingDataViewModel.switchState[2],
        ttsVolume = settingDataViewModel.ttsVolume,

        repeat = settingDataViewModel.switchState[3],
        repeatMinute = settingDataViewModel.repeatMinute,

        rem = settingDataViewModel.switchState[4]
    )
}