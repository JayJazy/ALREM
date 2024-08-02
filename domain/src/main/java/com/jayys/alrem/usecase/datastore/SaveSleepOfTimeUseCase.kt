package com.jayys.alrem.usecase.datastore

import com.jayys.alrem.repository.SleepOfTimeStateRepository
import javax.inject.Inject

class SaveSleepOfTimeUseCase  @Inject constructor(private val sleepOfTimeStateRepository: SleepOfTimeStateRepository){

    suspend operator fun invoke(state : Boolean) = sleepOfTimeStateRepository.saveSleepOfTimeState(state)
}