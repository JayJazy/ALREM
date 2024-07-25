package com.jayys.alrem.broadcastreceiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.jayys.alrem.broadcastreceiver.alarmevent.alarmBell
import com.jayys.alrem.broadcastreceiver.alarmevent.alarmRepeat
import com.jayys.alrem.broadcastreceiver.alarmevent.alarmTTS
import com.jayys.alrem.broadcastreceiver.alarmevent.alarmVibration
import com.jayys.alrem.broadcastreceiver.notification.createNormalNotification
import com.jayys.alrem.broadcastreceiver.notification.createSleepTimeNotification
import com.jayys.alrem.destination.DestinationAlarmActivity
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.alarm.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import com.jayys.alrem.usecase.datastore.SaveWakeUpTimeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    @Inject
    lateinit var getAllAlarmsUseCase: GetAllAlarmsUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        val requestCode = intent?.getIntExtra("requestCode", -1)
        val action = intent?.getStringExtra("action")

        context?.let{ ctx ->
            if (requestCode == 0 || action == "goToBedTime") {
                createSleepTimeNotification(ctx)
                return
            }
        }

        launch {
            try {
                val alarm = withContext(Dispatchers.IO) {
                    getAllAlarmsUseCase()
                        .map { alarmList -> alarmList.firstOrNull { it.id == requestCode } }
                        .firstOrNull()
                }
                alarm?.let { alarmData ->
                    context?.let { context ->
                        context.startActivity(startActivityIntent(context, alarmData))
                        createNormalNotification(context, alarmData)
                        saveCurrentAlarm(context, alarmData)
                        checkAlarmProperties(context, alarmData)
                    }

                }
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "예외 발생: ${e.message}")
            }
        }


    }

    private fun saveCurrentAlarm(context: Context, alarm: AlarmEntity) {
        val sharedPref = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("currentAlarm", Gson().toJson(alarm))
            apply()
        }
    }

    private fun startActivityIntent(context: Context, alarm: AlarmEntity): Intent{
        val gson = Gson()
        val alarmJson = gson.toJson(alarm)
        return Intent(context, DestinationAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("alarm", alarmJson)
        }
    }

    private fun checkAlarmProperties(context: Context, alarm: AlarmEntity) {

        if(alarm.bell) { alarmBell(context, alarm) }

        if(alarm.vibration) { alarmVibration(context) }

        if(alarm.tts) { alarmTTS(context, alarm) }

        if(alarm.repeat) { alarmRepeat(context, alarm) }

    }
}