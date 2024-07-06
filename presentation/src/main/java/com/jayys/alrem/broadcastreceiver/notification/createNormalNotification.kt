package com.jayys.alrem.broadcastreceiver.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.jayys.alrem.R
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity

private const val CHANNEL_ID = "NormalNotification"

fun createNormalNotification(context: Context, alarm: AlarmEntity) {

    val requestCode = alarm.id

    createChannel(requestCode, context, CHANNEL_ID)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val contentText = alarm.title.ifEmpty { "지금 시간에 설정한 알람이 있어요" }

    val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", requestCode)
        putExtra("action", "dismiss")
    }
    val dismissPendingIntent = PendingIntent.getBroadcast(context, requestCode, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val notification = NotificationCompat.Builder(context, "$CHANNEL_ID$requestCode")
        .setContentTitle("알람")
        .setContentText(contentText)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(false)
        .setOngoing(true)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .addAction(requestCode,"알림 해제", dismissPendingIntent)
        .build()

    notificationManager.notify(requestCode, notification)
}