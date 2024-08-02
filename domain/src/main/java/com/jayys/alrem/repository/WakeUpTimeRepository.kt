package com.jayys.alrem.repository

import java.time.LocalDateTime

interface WakeUpTimeRepository {

    /** DataStore  **/
    suspend fun saveWakeUpTime(wakeUpTime : LocalDateTime)

    suspend fun loadWakeUpTime() : LocalDateTime
}