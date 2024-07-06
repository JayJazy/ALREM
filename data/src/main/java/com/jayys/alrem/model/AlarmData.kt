package com.jayys.alrem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "alarm_table")
data class AlarmData(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val pageNum : Int,
    val title : String,
    val alarmDate : List<Date>,
    val alarmDayOfWeek : List<Boolean>,

    val bellName : String,
    val bellRingtone : String,
    val bell : Boolean,
    val bellVolume : Int,

    val vibration : Boolean,

    val tts : Boolean,
    val ttsVolume : Int,

    val repeat : Boolean,
    val repeatMinute : Int,

    val rem : Boolean

)