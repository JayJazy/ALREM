package com.jayys.alrem.usecase

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject


class AddAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository){

    suspend operator fun invoke(alarm : AlarmEntity) = alarmRepository.addAlarm(alarm)
}