package com.jayys.alrem.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jayys.alrem.broadcastreceiver.alarmreset.AlarmResetWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val request = OneTimeWorkRequestBuilder<AlarmResetWorker>().build()
            workManager.enqueue(request)
        }

        if (intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            val request = OneTimeWorkRequestBuilder<AlarmResetWorker>().build()
            workManager.enqueue(request)
        }
    }
}