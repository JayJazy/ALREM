package com.jayys.alrem.broadcastreceiver.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log


fun createChannel(requestCode: Int, context: Context, channelID: String)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "$channelID$requestCode"
        val descriptionText = "Channel for notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("$channelID$requestCode", name, importance).apply {
            description = descriptionText
            enableLights(true)
            enableVibration(true)
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}