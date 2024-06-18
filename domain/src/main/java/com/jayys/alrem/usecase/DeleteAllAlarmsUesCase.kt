package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class DeleteAllAlarmsUesCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke() = alarmRepository.deleteAllAlarms()
}