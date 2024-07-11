package com.jayys.alrem.broadcastreceiver.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.jayys.alrem.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val CHANNEL_ID = "SleepTimeNotification"

fun createSleepTimeNotification(context: Context)
{
    createChannel(0, context, CHANNEL_ID)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val requestCode = 0

    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = dateFormat.format(currentTime)


    val notification = NotificationCompat.Builder(context, "$CHANNEL_ID$requestCode")
        .setContentTitle("잠잘 시간이에요")
        .setContentText("취침 시간 : $formattedTime")
        .setSmallIcon(R.drawable.alrem_notification)
        .setColor(ContextCompat.getColor(context, R.color.MainBackground))
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(true)
        .setOngoing(false)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .build()

    notificationManager.notify(requestCode, notification)
}