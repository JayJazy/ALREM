package com.jayys.alrem.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime



interface DataStoreRepository {

    /** DataStore  **/
    suspend fun saveSwitchState(position : Int, isChecked : Boolean)

    suspend fun loadSwitchState(position: Int): Boolean

    suspend fun loadAllSwitchStates(): Map<Int, Boolean>


    suspend fun saveWakeUpTime(wakeUpTime : LocalDateTime)

    suspend fun loadWakeUpTime() : LocalDateTime


    suspend fun saveOnBoardingState(state : Boolean)

    fun loadOnBoardingState() : Flow<Boolean>



    suspend fun saveSleepOfTimeState(state : Boolean)

    fun loadSleepOfTimeState() : Flow<Boolean>

}