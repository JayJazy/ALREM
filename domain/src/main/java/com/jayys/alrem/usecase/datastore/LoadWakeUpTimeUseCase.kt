package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.DataStoreRepository
import javax.inject.Inject

class LoadWakeUpTimeUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke() = dataStoreRepository.loadWakeUpTime()
}