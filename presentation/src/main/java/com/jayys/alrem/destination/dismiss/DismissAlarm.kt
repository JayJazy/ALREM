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
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
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
    val dayMap = mapOf("일" to Calendar.SUNDAY, "월" to Calendar.MONDAY, "화" to Calendar.TUESDAY,
        "수" to Calendar.WEDNESDAY, "목" to Calendar.THURSDAY, "금" to Calendar.FRIDAY, "토" to Calendar.SATURDAY)

    val candidateDates = mutableListOf<Calendar>()

    for (day in selectedDays) {
        val targetDay = dayMap[day] ?: continue
        val nextOccurrence = now.clone() as Calendar
        nextOccurrence.set(Calendar.HOUR_OF_DAY, 0)
        nextOccurrence.set(Calendar.MINUTE, 0)
        nextOccurrence.set(Calendar.SECOND, 0)
        nextOccurrence.set(Calendar.MILLISECOND, 0)

        while (nextOccurrence.get(Calendar.DAY_OF_WEEK) != targetDay) {
            nextOccurrence.add(Calendar.DAY_OF_YEAR, 1)
        }

        if (nextOccurrence.before(now)) {
            nextOccurrence.add(Calendar.WEEK_OF_YEAR, 1)
        }

        candidateDates.add(nextOccurrence)
    }

    val closestDate = candidateDates.minByOrNull { it.timeInMillis }?.time

    return alarmDates.filter { it.after(closestDate) }.minOrNull() ?: closestDate
}