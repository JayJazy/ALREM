package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.SleepOfTimeStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadSleepOfTimeUseCase @Inject constructor(private val sleepOfTimeStateRepository: SleepOfTimeStateRepository) {

    operator fun invoke() : Flow<Boolean> = sleepOfTimeStateRepository.loadSleepOfTimeState()
}