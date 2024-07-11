package com.jayys.alrem.usecase.datastore

import com.jayys.alrem.repository.DataStoreRepository
import javax.inject.Inject

class SaveSleepOfTimeUseCase  @Inject constructor(private val dataStoreRepository: DataStoreRepository){

    suspend operator fun invoke(state : Boolean) = dataStoreRepository.saveSleepOfTimeState(state)
}