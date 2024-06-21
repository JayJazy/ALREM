package com.jayys.alrem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jayys.alrem.model.AlarmData
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao{

    /** 조회 **/
    @Query("SELECT * FROM alarm_table ORDER BY id DESC")
    fun getAllAlarms() : Flow<List<AlarmData>>


    /** 추가 **/
    @Insert
    fun addAlarm(alarm : AlarmData)


    /** 업데이트 **/
    @Update
    fun updateAlarm(alarm : AlarmData)



    /** 삭제 **/
    @Query("DELETE  FROM alarm_table WHERE id = :id")
    fun deleteAlarm(id : Int)

    @Query("DELETE FROM alarm_table")
    suspend fun deleteAllAlarms()



}