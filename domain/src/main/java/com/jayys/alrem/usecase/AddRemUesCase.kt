package com.jayys.alrem.usecase

import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject

class AddRemUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {

    suspend operator fun invoke(rem : RemEntity) = alarmRepository.addRem(rem)
}