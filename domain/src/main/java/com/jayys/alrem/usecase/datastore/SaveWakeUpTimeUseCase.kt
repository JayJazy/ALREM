package com.jayys.alrem.usecase.datastore

import com.jayys.alrem.repository.DataStoreRepository
import java.time.LocalDateTime
import javax.inject.Inject

class SaveWakeUpTimeUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(wakeUpTime : LocalDateTime) = dataStoreRepository.saveWakeUpTime(wakeUpTime)
}