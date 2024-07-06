package com.jayys.alrem.mapper

import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.model.RemData

object RemMapper : Mapper<RemData, RemEntity>{
    override fun RemData.mapToDomainModel(): RemEntity {
        return RemEntity(id, remDate, sleepingTime)
    }

    override fun RemEntity.mapFromDomainModel(): RemData {
        return RemData(id, remDate, sleepingTime)
    }
}