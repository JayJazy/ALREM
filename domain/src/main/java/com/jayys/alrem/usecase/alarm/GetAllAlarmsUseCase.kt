package com.jayys.alrem.usecase.alarm

import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllAlarmsUseCase @Inject constructor(private val alarmRepository: AlarmRepository ) {

    suspend operator fun invoke() : Flow<List<AlarmEntity>> = alarmRepository.getAllAlarms()
}