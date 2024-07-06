package com.jayys.alrem.usecase

import com.jayys.alrem.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadSleepOfTimeUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    operator fun invoke() : Flow<Boolean> = alarmRepository.loadSleepOfTimeState()
}