package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.WakeUpTimeRepository
import javax.inject.Inject

class LoadWakeUpTimeUseCase @Inject constructor(private val wakeUpTimeRepository: WakeUpTimeRepository) {

    suspend operator fun invoke() = wakeUpTimeRepository.loadWakeUpTime()
}