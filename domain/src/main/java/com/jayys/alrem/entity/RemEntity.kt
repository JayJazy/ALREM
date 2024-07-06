package com.jayys.alrem.entity

import java.time.LocalDateTime


data class RemEntity (
    val id : Int,
    val remDate : LocalDateTime,
    val sleepingTime : Long
)