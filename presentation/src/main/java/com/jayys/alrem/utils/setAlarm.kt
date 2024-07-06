package com.jayys.alrem.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity
import java.util.Calendar



fun setAlarm(alarm: AlarmEntity, context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val now = Calendar.getInstance().time
    val closestDate = alarm.alarmDate.filter { it.after(now) }.minOrNull() ?: return

    val calendar = Calendar.getInstance().apply {
        time = closestDate
    }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", alarm.id)
    }

    val pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

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
