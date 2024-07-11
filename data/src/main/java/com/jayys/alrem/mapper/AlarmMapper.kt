package com.jayys.alrem.mapper

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.model.AlarmData


object AlarmMapper : Mapper<AlarmData, AlarmEntity>{
    override fun AlarmData.mapToDomainModel(): AlarmEntity {
        return AlarmEntity(id, pageNum, title, alarmDate, alarmDayOfWeek, bellName, bellRingtone, bell, bellVolume, vibration, tts, ttsVolume, repeat, repeatMinute, rem)
    }

    override fun AlarmEntity.mapFromDomainModel(): AlarmData {
        return AlarmData(id, pageNum, title, alarmDate, alarmDayOfWeek, bellName, bellRingtone, bell, bellVolume, vibration, tts, ttsVolume, repeat, repeatMinute, rem)
    }
}