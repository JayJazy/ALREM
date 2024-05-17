package com.jayys.alrem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "alarm_table")
data class AlarmData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val alarmDate : Date,

    val bellName : String,
    val bell : Boolean,
    val bellVolume : Float,

    val vibration : Boolean,
    val vibVolume : Float,

    val tts : Boolean,
    val ttsVolume : Float,

    val repeat : Int,

    val rem : Boolean

)