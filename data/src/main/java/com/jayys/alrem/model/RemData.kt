package com.jayys.alrem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "rem_table")
data class RemData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val remDate : LocalDateTime,
    val sleepingTime : Long
)