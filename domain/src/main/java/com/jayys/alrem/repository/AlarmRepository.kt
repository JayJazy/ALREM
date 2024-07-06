package com.jayys.alrem.repository

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.entity.RemEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.Date

interface AlarmRepository {

    /** 조회  **/
    suspend fun getAllAlarms() : Flow<List<AlarmEntity>>

    suspend fun getMaxId() : Int

    suspend fun getAllRems() : Flow<List<RemEntity>>


    /** 추가 **/
    suspend fun addAlarm(alarm : AlarmEntity)

    suspend fun addRem(rem : RemEntity)


    /** 변경  **/
    suspend fun updateAlarm(alarm : AlarmEntity)


    /** 삭제  **/
    suspend fun deleteAlarm(id : Int)

    suspend fun deleteAllAlarms()


    /** DataStore  **/
    suspend fun saveSwitchState(position : Int, isChecked : Boolean)

    suspend fun loadSwitchState(position: Int): Boolean

    suspend fun loadAllSwitchStates(): Map<Int, Boolean>


    suspend fun saveWakeUpTime(wakeUpTime : LocalDateTime)

    suspend fun loadWakeUpTime() : LocalDateTime



    suspend fun saveSleepOfTimeState(state : Boolean)

    fun loadSleepOfTimeState() : Flow<Boolean>

}