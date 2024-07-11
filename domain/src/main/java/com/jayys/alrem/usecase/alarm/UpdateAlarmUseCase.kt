package com.jayys.alrem.usecase.alarm

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke(alarm : AlarmEntity) = alarmRepository.updateAlarm(alarm)
}