package com.jayys.alrem.destination

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.jayys.alrem.entity.AlarmEntity
import java.util.Calendar

fun initAlarm(
    context: Context,
    selectedDays: List<String>,
    alarmManager: AlarmManager,
    notificationManager: NotificationManager,
    cancelPendingIntent: PendingIntent,
    resetAlarmPendingIntent: PendingIntent,
    alarm: AlarmEntity,
    calendar: Calendar
)
{
    alarmManager.cancel(cancelPendingIntent)
    clearCurrentAlarm(context)
    if(selectedDays.isNotEmpty())
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), resetAlarmPendingIntent)
            }
        }
        else {
            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), resetAlarmPendingIntent)
        }
        Toast.makeText(context, "다음 알람이 설정되었습니다", Toast.LENGTH_SHORT).show()
    }
    notificationManager.cancel(alarm.id)
    notificationManager.deleteNotificationChannel("NormalNotification${alarm.id}")
}
