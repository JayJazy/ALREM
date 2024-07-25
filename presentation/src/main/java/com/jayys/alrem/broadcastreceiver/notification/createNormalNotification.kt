package com.jayys.alrem.broadcastreceiver.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.jayys.alrem.R
import com.jayys.alrem.destination.DestinationAlarmActivity
import com.jayys.alrem.entity.AlarmEntity

private const val CHANNEL_ID = "NormalNotification"

fun createNormalNotification(context: Context, alarm: AlarmEntity) {

    val requestCode = alarm.id

    createChannel(requestCode, context, CHANNEL_ID)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val contentTitle= alarm.title.ifEmpty { "알람" }

    val gson = Gson()
    val alarmJson = gson.toJson(alarm)
    val intent = Intent(context, DestinationAlarmActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        putExtra("alarm", alarmJson)
    }
    val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val notification =  NotificationCompat.Builder(context, "$CHANNEL_ID$requestCode")
        .setContentTitle(contentTitle)
        .setContentText("알림을 눌려 알람을 해제해 주세요")
        .setSmallIcon(R.drawable.alrem_notification)
        .setColor(ContextCompat.getColor(context, R.color.MainBackground))
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(false)
        .setOngoing(true)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(requestCode, notification)
}