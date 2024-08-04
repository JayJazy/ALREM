package com.jayys.alrem.destination.dismiss

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class DismissAlarm @Inject constructor() {

    fun getCancelPendingIntent(id: Int, context: Context): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    fun resetCalendar(alarm: AlarmEntity, selectedDays: List<String>): Calendar {
        val now = Calendar.getInstance()
        now.add(Calendar.MINUTE, 5)

        val closestDate = findClosestDate(now, selectedDays, alarm.alarmDate)
        val calendar = Calendar.getInstance()

        if (closestDate != null) {
            calendar.apply {
                time = closestDate
            }
        }
        return calendar
    }
}

private fun findClosestDate(now: Calendar, selectedDays: List<String>, alarmDates: List<Date>): Date? {
    val dayMap = mapOf(
        "일" to Calendar.SUNDAY,
        "월" to Calendar.MONDAY,
        "화" to Calendar.TUESDAY,
        "수" to Calendar.WEDNESDAY,
        "목" to Calendar.THURSDAY,
        "금" to Calendar.FRIDAY,
        "토" to Calendar.SATURDAY)

    val candidateDates = mutableListOf<Date>()
    val firstAlarmDate = alarmDates.first()
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

            if (before(now)) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }.time

        candidateDates.add(closestAlarmDate)
    }

    return candidateDates.minByOrNull { it.time - now.timeInMillis }

}