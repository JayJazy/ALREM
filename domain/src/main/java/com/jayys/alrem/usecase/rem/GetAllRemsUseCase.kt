package com.jayys.alrem.usecase.rem

import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.repository.RemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllRemsUseCase @Inject constructor(private val remRepository: RemRepository){

    suspend operator fun invoke() : Flow<List<RemEntity>> = remRepository.getAllRems()
}