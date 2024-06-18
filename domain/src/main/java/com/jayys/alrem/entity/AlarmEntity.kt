package com.jayys.alrem.entity

import java.util.Date


data class AlarmEntity(
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