package com.jayys.alrem.repository

interface SwitchStateRepository  {

    /** DataStore  **/
    suspend fun saveSwitchState(position : Int, isChecked : Boolean)

    suspend fun loadSwitchState(position: Int): Boolean

    suspend fun loadAllSwitchStates(): Map<Int, Boolean>
}