package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class SaveSleepOfTimeUseCase  @Inject constructor(private val alarmRepository: AlarmRepository){

    suspend operator fun invoke(state : Boolean) = alarmRepository.saveSleepOfTimeState(state)
}