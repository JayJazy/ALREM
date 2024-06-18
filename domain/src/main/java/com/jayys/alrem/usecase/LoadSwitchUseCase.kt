package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class LoadSwitchUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke(position : Int) = alarmRepository.loadSwitchState(position)
}