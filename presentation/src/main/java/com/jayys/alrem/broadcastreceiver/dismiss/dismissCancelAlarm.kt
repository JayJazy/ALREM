package com.jayys.alrem.broadcastreceiver.dismiss

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.broadcastreceiver.notification.createRemNotification
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import com.jayys.alrem.utils.cancelExistingAlarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.system.exitProcess


fun dismissCancelAlarm(
    context: Context,
    alarm: AlarmEntity,
    saveSwitchUseCase: SaveSwitchUseCase
)
{
    val requestCode = alarm.id

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val allDays = setOf("일", "월", "화", "수", "목", "금", "토")
    val emptyDay = allDays.filterIndexed { index, _ -> alarm.alarmDayOfWeek[index] }


    if(emptyDay.isNotEmpty())
    {
        cancelExistingAlarm(requestCode, context)

        val now = Calendar.getInstance().time
        val closestDate = alarm.alarmDate.filter { it.after(now) }.minOrNull() ?: return

        val calendar = Calendar.getInstance().apply {
            time = closestDate
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("requestCode", requestCode)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

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
    else
    {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("requestCode", requestCode)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)


        // DataStore 스위치 false 저장
        CoroutineScope(Dispatchers.IO).launch {
            saveSwitchUseCase.invoke(requestCode, false)
        }
    }

    if(alarm.rem)
    {
        createRemNotification(context, alarm)
    }

    Handler(Looper.getMainLooper()).postDelayed({
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.appTasks.forEach { it.finishAndRemoveTask() }
        exitProcess(0)
    },1000)
}
