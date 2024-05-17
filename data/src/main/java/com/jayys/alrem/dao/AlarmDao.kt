package com.jayys.alrem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jayys.alrem.model.AlarmData
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao{

    @Query("SELECT * FROM alarm_table ORDER BY id ASC")
    fun getAllAlarmData() : Flow<List<AlarmData>>

    @Insert
    fun addAlarm(alarm : AlarmData)

    @Update
    fun updateAlarm(alarm : AlarmData)



}