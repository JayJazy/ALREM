package com.jayys.alrem.usecase.datastore

import com.jayys.alrem.repository.DataStoreRepository
import javax.inject.Inject


class SaveSwitchUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(position : Int, isChecked : Boolean) = dataStoreRepository.saveSwitchState(position, isChecked)
}