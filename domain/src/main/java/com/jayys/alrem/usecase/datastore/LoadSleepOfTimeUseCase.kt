package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadSleepOfTimeUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke() : Flow<Boolean> = dataStoreRepository.loadSleepOfTimeState()
}