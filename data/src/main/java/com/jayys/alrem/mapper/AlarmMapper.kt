package com.jayys.alrem.mapper

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.model.AlarmData
import java.util.Date

object AlarmMapper : Mapper<AlarmData, AlarmEntity>{
    override fun AlarmData.mapToDomainModel(): AlarmEntity {
        return AlarmEntity(id, title, alarmDate, bellName, bellRingtone, bell, bellVolume, vibration, vibVolume, tts, ttsVolume, repeat, repeatMinute, rem)
    }

    override fun AlarmEntity.mapFromDomainModel(): AlarmData {
        return AlarmData(id, title, alarmDate, bellName, bellRingtone, bell, bellVolume, vibration, vibVolume, tts, ttsVolume, repeat, repeatMinute, rem)
    }
}