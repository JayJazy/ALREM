package com.jayys.alrem.broadcastreceiver.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.jayys.alrem.MainActivity
import com.jayys.alrem.R
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity

private const val CHANNEL_ID = "RemNotification"

fun createRemNotification(context: Context, alarm: AlarmEntity)
{
    val requestCode = alarm.id

    createChannel(requestCode, context, CHANNEL_ID)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("requestCode", requestCode)
        putExtra("TimeOfSleep", true)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


    val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("action", "dismissNotification")
    }
    val dismissPendingIntent = PendingIntent.getBroadcast(context, requestCode, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



    val notification = NotificationCompat.Builder(context, "$CHANNEL_ID$requestCode")
        .setContentTitle("알람")
        .setContentText("알림을 클릭해서 잠들었던 시간을 설정해 주세요")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(true)
        .setOngoing(true)
        .setContentIntent(pendingIntent)
        .setDeleteIntent(dismissPendingIntent)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .build()

    notificationManager.notify(requestCode, notification)
}