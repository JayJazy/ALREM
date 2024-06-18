package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class SaveSwitchUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke(position : Int, isChecked : Boolean) = alarmRepository.saveSwitchState(position, isChecked)
}