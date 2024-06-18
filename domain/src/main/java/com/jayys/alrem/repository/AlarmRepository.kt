package com.jayys.alrem.repository

import com.jayys.alrem.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    suspend fun addAlarm(alarm : AlarmEntity)

    suspend fun updateAlarm(alarm : AlarmEntity)

    suspend fun deleteAlarm(id : Int)

    suspend fun getAllAlarms() : Flow<List<AlarmEntity>>



    suspend fun deleteAllAlarms()


    suspend fun saveSwitchState(position : Int, isChecked : Boolean)

    suspend fun loadSwitchState(position: Int): Boolean
}