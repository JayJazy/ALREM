package com.jayys.alrem.screen.alarmadd.update

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import java.util.Calendar

fun updateAlarmDataToSettingData(
    updateAlarmData: AlarmEntity
) : SettingData
{

    val switchStates = mutableListOf(updateAlarmData.bell, updateAlarmData.vibration, updateAlarmData.tts, updateAlarmData.repeat, updateAlarmData.rem)
    val calendar = Calendar.getInstance().apply { time = updateAlarmData.alarmDate.first() }
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)


    return SettingData(
        isUpdate = true,
        pageNumber = updateAlarmData.pageNum,
        alarmName = updateAlarmData.title,

        dayOfWeekList = updateAlarmData.alarmDayOfWeek.toMutableList(),
        hour = hour,
        min = minute,

        bellName = updateAlarmData.bellName,
        ringtoneStringUri = updateAlarmData.bellRingtone,
        bellVolume = updateAlarmData.bellVolume,

        ttsVolume = updateAlarmData.ttsVolume,

        repeatMinute = updateAlarmData.repeatMinute,

        switchState = switchStates
    )

}
