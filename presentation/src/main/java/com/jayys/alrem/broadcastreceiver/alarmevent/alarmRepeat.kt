package com.jayys.alrem.broadcastreceiver.alarmevent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity


fun alarmRepeat(context : Context, alarm : AlarmEntity)
{
    val requestCode = alarm.id

    val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    val repeatIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", requestCode)
    }
    val pendingIntent = PendingIntent.getBroadcast(context, requestCode, repeatIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.add(Calendar.MINUTE, alarm.repeatMinute)

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent)
            }
        }
        else
        {
            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent)
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}