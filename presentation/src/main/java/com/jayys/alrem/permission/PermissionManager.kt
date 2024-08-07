package com.jayys.alrem.permission

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionManager(private val activity: Activity) {

    companion object {
        const val REQUEST_CODE_POST_NOTIFICATIONS_PERMISSION = 1000
        const val REQUEST_CODE_PERMISSION = 1001
        const val WAKE_LOCK_PERMISSION_REQUEST_CODE = 1002
    }

    private val packageName = activity.packageName

    // 알람 및 remainder
    fun checkAndRequestExactAlarmPermissions() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:${packageName}"))
                activity.startActivity(intent)
                return false
            }
        }
        else
        {
            if (activity.checkSelfPermission(android.Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WAKE_LOCK), WAKE_LOCK_PERMISSION_REQUEST_CODE)
                return false
            }
        }
        return true
    }

    // Notification 알림
    fun checkAndRequestNotificationPermissions() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_POST_NOTIFICATIONS_PERMISSION)
                return false
            }
        }
        else
        {
            val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.areNotificationsEnabled()) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                activity.startActivity(intent)
                return false
            }
        }
        return true
    }


    // Notification 감지
    /*
    fun checkAndRequestNotificationServicePermissions(): Boolean {
        val cn = ComponentName(activity, AlarmNotificationListener::class.java)
        val flat = Settings.Secure.getString(activity.contentResolver, "enabled_notification_listeners")
        val isEnabled = flat != null && flat.contains(cn.flattenToString())

        if (!isEnabled) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            activity.startActivity(intent)
            return false
        }
        return true
    }*/



    // 다른 앱 위에 표시
    fun checkAndRequestOverlayPermission() : Boolean {
        if (!Settings.canDrawOverlays(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            activity.startActivity(intent)
            return false
        }
        return true
    }


    // 배터리 최적화 무시
    fun checkAndRequestIgnoreBatteryOptimizations() : Boolean {
        val powerManager = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:$packageName"))
            activity.startActivity(intent)
            return false
        }
        return true
    }

    // 배터리 최적화 무시 체크
    fun checkIgnoreBatteryOptimizations() : Boolean{
        val powerManager = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(packageName)
    }


    // 내부 저장소
    fun checkAndRequestStoragePermissions() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),
                    REQUEST_CODE_PERMISSION
                )
                return false
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
                return false
            }
        }
        return true
    }
}