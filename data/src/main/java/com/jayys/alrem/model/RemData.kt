package com.jayys.alrem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "rem_table")
data class RemData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val remData : Date,
    val sleepingTime : Int
)