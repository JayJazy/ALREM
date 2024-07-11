package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.DataStoreRepository
import javax.inject.Inject

class LoadSwitchUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(position : Int) = dataStoreRepository.loadSwitchState(position)

    suspend fun loadAll() = dataStoreRepository.loadAllSwitchStates()
}