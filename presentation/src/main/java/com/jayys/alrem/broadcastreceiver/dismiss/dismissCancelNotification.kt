package com.jayys.alrem.broadcastreceiver.dismiss

import android.app.NotificationManager
import android.content.Context
import com.jayys.alrem.entity.AlarmEntity


fun dismissCancelNotification(context: Context, alarm: AlarmEntity)
{
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(alarm.id)
}