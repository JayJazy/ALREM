package com.jayys.alrem.repository

import kotlinx.coroutines.flow.Flow

interface OnBoardingStateRepository {

    /** DataStore  **/
    suspend fun saveOnBoardingState(state : Boolean)

    fun loadOnBoardingState() : Flow<Boolean>
}