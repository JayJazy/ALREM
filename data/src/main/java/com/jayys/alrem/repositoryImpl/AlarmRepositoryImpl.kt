package com.jayys.alrem.repositoryImpl


import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.mapper.AlarmMapper.mapFromDomainModel
import com.jayys.alrem.mapper.AlarmMapper.mapToDomainModel
import com.jayys.alrem.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao : AlarmDao
) : AlarmRepository {


    override suspend fun getAllAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarms().map { alarmList ->
            alarmList.map { alarmData ->
                alarmData.mapToDomainModel()
            }
        }
    }

    override suspend fun getMaxId(): Int {
        return alarmDao.getMaxId()
    }




    override suspend fun addAlarm(alarm: AlarmEntity) {
        alarmDao.addAlarm(alarm.mapFromDomainModel())
    }



    override suspend fun updateAlarm(alarm: AlarmEntity) {
        alarmDao.updateAlarm(alarm.mapFromDomainModel())
    }


    override suspend fun deleteAllAlarms() {
        alarmDao.deleteAllAlarms()
    }

    override suspend fun deleteAlarm(id: Int) {
        alarmDao.deleteAlarm(id)
    }




}