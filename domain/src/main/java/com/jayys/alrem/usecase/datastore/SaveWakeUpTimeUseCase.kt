package com.jayys.alrem.usecase.datastore

import com.jayys.alrem.repository.WakeUpTimeRepository
import java.time.LocalDateTime
import javax.inject.Inject

class SaveWakeUpTimeUseCase @Inject constructor(private val wakeUpTimeRepository: WakeUpTimeRepository) {

    suspend operator fun invoke(wakeUpTime : LocalDateTime) = wakeUpTimeRepository.saveWakeUpTime(wakeUpTime)
}