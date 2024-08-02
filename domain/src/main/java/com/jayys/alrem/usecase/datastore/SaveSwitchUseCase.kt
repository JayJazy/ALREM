package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.SwitchStateRepository
import javax.inject.Inject


class SaveSwitchUseCase @Inject constructor(private val switchStateRepository: SwitchStateRepository) {

    suspend operator fun invoke(position : Int, isChecked : Boolean) = switchStateRepository.saveSwitchState(position, isChecked)
}