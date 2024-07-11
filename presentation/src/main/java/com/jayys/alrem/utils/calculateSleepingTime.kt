package com.jayys.alrem.utils

import java.time.Duration
import java.time.LocalDateTime


fun calculateSleepTime(wakeUpTime: LocalDateTime, selectedHour: Int, selectedMin: Int) : Long
{
    val sleepTime = wakeUpTime.withHour(selectedHour).withMinute(selectedMin).withSecond(0).withNano(0)
    val adjustedSleepTime = if (sleepTime.isAfter(wakeUpTime)) sleepTime.minusDays(1) else sleepTime

    return Duration.between(adjustedSleepTime, wakeUpTime).seconds
}