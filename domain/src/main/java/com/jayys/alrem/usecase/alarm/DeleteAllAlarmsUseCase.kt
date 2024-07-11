package com.jayys.alrem.usecase.alarm

import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class DeleteAllAlarmsUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke() = alarmRepository.deleteAllAlarms()
}