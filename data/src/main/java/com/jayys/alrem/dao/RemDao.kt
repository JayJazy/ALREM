package com.jayys.alrem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jayys.alrem.model.RemData
import kotlinx.coroutines.flow.Flow


@Dao
interface RemDao {

    @Query("SELECT * FROM rem_table ORDER BY id ASC")
    fun getAllRemData() : Flow<List<RemData>>

    @Insert
    fun addRem(rem : RemData)
}