package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.OnBoardingStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadOnBoardingUseCase @Inject constructor(private val onBoardingStateRepository: OnBoardingStateRepository) {

    operator fun invoke() : Flow<Boolean> = onBoardingStateRepository.loadOnBoardingState()
}