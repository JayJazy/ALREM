package com.jayys.alrem.usecase

import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllRemsUseCase @Inject constructor(private val alarmRepository: AlarmRepository){

    suspend operator fun invoke() : Flow<List<RemEntity>> = alarmRepository.getAllRems()
}