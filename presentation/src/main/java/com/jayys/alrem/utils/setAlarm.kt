package com.jayys.alrem.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity
import java.util.Calendar
import java.util.Date


fun setAlarm(alarm: AlarmEntity, context: Context, isAlreadyAlarm: Boolean) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val now = Calendar.getInstance().time
    val closestDate = if(isAlreadyAlarm)
    {
        val today = Calendar.getInstance()
        val allDays = setOf("일", "월", "화", "수", "목", "금", "토")
        val selectedDays = allDays.filterIndexed { index, _ -> alarm.alarmDayOfWeek[index] }

        val dayMap = mapOf(
            "일" to Calendar.SUNDAY,
            "월" to Calendar.MONDAY,
            "화" to Calendar.TUESDAY,
            "수" to Calendar.WEDNESDAY,
            "목" to Calendar.THURSDAY,
            "금" to Calendar.FRIDAY,
            "토" to Calendar.SATURDAY)

        val candidateDates = mutableListOf<Date>()
        val firstAlarmDate = alarm.alarmDate.first()
        val calendar = Calendar.getInstance().apply {
            time = firstAlarmDate
        }

        val alarmHour = calendar.get(Calendar.HOUR_OF_DAY)
        val alarmMin = calendar.get(Calendar.MINUTE)

        for(day in selectedDays)
        {
            val targetDay = dayMap[day] ?: continue
            val closestAlarmDate = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, targetDay)
                set(Calendar.HOUR_OF_DAY, alarmHour)
                set(Calendar.MINUTE, alarmMin)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (time.before(now)) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }.time

            candidateDates.add(closestAlarmDate)
        }

        candidateDates.minByOrNull { it.time - today.timeInMillis } ?: alarm.alarmDate.filter { it.after(now) }.min()
    }

    else alarm.alarmDate.filter { it.after(now) }.min()


    val calendar = Calendar.getInstance().apply {
        time = closestDate
    }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", alarm.id)
    }


    val pendingIntent = if(isAlreadyAlarm){
        PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    else {
        PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }


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
