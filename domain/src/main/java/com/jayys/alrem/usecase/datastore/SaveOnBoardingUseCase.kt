package com.jayys.alrem.usecase.datastore


import com.jayys.alrem.repository.OnBoardingStateRepository
import javax.inject.Inject

class SaveOnBoardingUseCase @Inject constructor(private val onBoardingStateRepository: OnBoardingStateRepository) {

    suspend operator fun invoke(state : Boolean) = onBoardingStateRepository.saveOnBoardingState(state)
}