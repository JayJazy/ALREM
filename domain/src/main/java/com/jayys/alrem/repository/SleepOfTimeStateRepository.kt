package com.jayys.alrem.repository

import kotlinx.coroutines.flow.Flow

interface SleepOfTimeStateRepository {

    /** DataStore **/
    suspend fun saveSleepOfTimeState(state : Boolean)

    fun loadSleepOfTimeState() : Flow<Boolean>

}