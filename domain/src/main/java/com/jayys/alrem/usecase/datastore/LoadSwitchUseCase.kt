package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.SwitchStateRepository
import javax.inject.Inject

class LoadSwitchUseCase @Inject constructor(private val switchStateRepository: SwitchStateRepository) {

    suspend operator fun invoke(position : Int) = switchStateRepository.loadSwitchState(position)

    suspend fun loadAll() = switchStateRepository.loadAllSwitchStates()
}