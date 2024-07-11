package com.jayys.alrem.repository

import com.jayys.alrem.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    /** 조회  **/
    suspend fun getAllAlarms() : Flow<List<AlarmEntity>>

    suspend fun getMaxId() : Int


    /** 추가 **/
    suspend fun addAlarm(alarm : AlarmEntity)


    /** 변경  **/
    suspend fun updateAlarm(alarm : AlarmEntity)


    /** 삭제  **/
    suspend fun deleteAlarm(id : Int)

    suspend fun deleteAllAlarms()




}