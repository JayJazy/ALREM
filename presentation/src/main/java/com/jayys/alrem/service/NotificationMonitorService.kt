package com.jayys.alrem.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.jayys.alrem.broadcastreceiver.AlarmReceiver


class NotificationMonitorService : NotificationListenerService() {
    private var isDismissed = false

    private val dismissReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            isDismissed = true
        }
    }

    override fun onCreate() {
        super.onCreate()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(dismissReceiver, IntentFilter("com.jayys.alrem.ACTION_NOTIFICATION_DISMISSED"))
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dismissReceiver)
        super.onDestroy()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)

        val channelId = sbn.notification.channelId
        val requestCode = sbn.id

        if (!isDismissed) {
            when {
                channelId.startsWith("NormalNotification") -> {
                    val intent = Intent(this, AlarmReceiver::class.java).apply {
                        putExtra("requestCode", requestCode)
                        putExtra("action", "createNormalNotification$requestCode")
                    }
                    sendBroadcast(intent)
                }
            }
        } else {
            isDismissed = false
            return
        }

    }
}