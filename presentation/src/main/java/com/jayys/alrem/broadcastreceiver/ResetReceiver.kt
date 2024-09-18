package com.jayys.alrem.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingWorkPolicy
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

        Log.d("AlarmWorker", "브로드캐스트 수신")

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("AlarmWorker", "재부팅 확인")
            val request = OneTimeWorkRequestBuilder<AlarmResetWorker>().build()
            workManager.enqueue(request)
        }
    }
}