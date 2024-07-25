package com.jayys.alrem.broadcastreceiver.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun createChannel(requestCode: Int, context: Context, channelID: String)
{
    val name = "$channelID$requestCode"
    val descriptionText = "Channel for notifications"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel("$channelID$requestCode", name, importance).apply {
        description = descriptionText
        enableLights(true)
        enableVibration(true)
        setShowBadge(false)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    }
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

}