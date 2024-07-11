package com.jayys.alrem.usecase.rem

import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.repository.RemRepository
import javax.inject.Inject

class AddRemUseCase @Inject constructor(private val remRepository: RemRepository) {

    suspend operator fun invoke(rem : RemEntity) = remRepository.addRem(rem)
}