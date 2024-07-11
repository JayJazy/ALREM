package com.jayys.alrem.repository

import com.jayys.alrem.entity.RemEntity
import kotlinx.coroutines.flow.Flow

interface RemRepository {

    /** 조회  **/
    suspend fun getAllRems() : Flow<List<RemEntity>>

    /** 추가 **/
    suspend fun addRem(rem : RemEntity)
}