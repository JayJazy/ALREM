package com.jayys.alrem.broadcastreceiver.alarmreset

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.repository.SwitchStateRepository
import com.jayys.alrem.usecase.alarm.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.datastore.LoadSwitchUseCase
import com.jayys.alrem.utils.setAlarm
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext


class AlarmResetWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AlarmResetWorkerEntryPoint {
        fun alarmRepository(): AlarmRepository
        fun switchStateRepository(): SwitchStateRepository
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            val hiltEntryPoint = EntryPointAccessors.fromApplication(
                applicationContext,
                AlarmResetWorkerEntryPoint::class.java
            )

            val alarmRepository = hiltEntryPoint.alarmRepository()
            val switchRepository = hiltEntryPoint.switchStateRepository()

            val getAllAlarmsUseCase = GetAllAlarmsUseCase(alarmRepository)
            val getLoadSwitchUseCase = LoadSwitchUseCase(switchRepository)


            val (alarms, switchStates) = withContext(Dispatchers.IO) {
                val switchStates = getLoadSwitchUseCase.loadAll().toMutableMap()
                val alarms = getAllAlarmsUseCase().firstOrNull() ?: emptyList()
                Pair(alarms, switchStates)
            }

            alarms.forEach { alarm ->
                if (!switchStates.containsKey(alarm.id)) {
                    switchStates[alarm.id] = true
                }
            }

            alarms.forEach { alarm ->
                val switchState = switchStates[alarm.id] ?: true

                if (switchState) {
                    setAlarm(alarm, applicationContext, true)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}