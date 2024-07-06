package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class SaveWakeUpTimeUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke(wakeUpTime : LocalDateTime) = alarmRepository.saveWakeUpTime(wakeUpTime)
}