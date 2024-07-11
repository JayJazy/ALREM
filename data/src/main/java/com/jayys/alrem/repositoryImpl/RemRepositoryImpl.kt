package com.jayys.alrem.repositoryImpl

import com.jayys.alrem.dao.RemDao
import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.mapper.RemMapper.mapFromDomainModel
import com.jayys.alrem.mapper.RemMapper.mapToDomainModel
import com.jayys.alrem.repository.RemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemRepositoryImpl @Inject constructor(
    private val remDao : RemDao
) : RemRepository {

    override suspend fun getAllRems(): Flow<List<RemEntity>> {
        return remDao.getAllRemData().map { remList ->
            remList.map { remData ->
                remData.mapToDomainModel()
            }
        }
    }

    override suspend fun addRem(rem: RemEntity) {
        remDao.addRem(rem.mapFromDomainModel())
    }
}