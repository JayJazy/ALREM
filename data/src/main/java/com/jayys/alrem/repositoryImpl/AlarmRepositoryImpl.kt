package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.mapper.AlarmMapper.mapFromDomainModel
import com.jayys.alrem.mapper.AlarmMapper.mapToDomainModel
import com.jayys.alrem.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao : AlarmDao,
    private val dataStore : DataStore<Preferences>
) : AlarmRepository {

    override suspend fun addAlarm(alarm: AlarmEntity) {
        alarmDao.addAlarm(alarm.mapFromDomainModel())
    }

    override suspend fun updateAlarm(alarm: AlarmEntity) {
        alarmDao.updateAlarm(alarm.mapFromDomainModel())
    }

    override suspend fun getAllAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarms().map {
            alarmList ->
            alarmList.map { alarmData ->
                alarmData.mapToDomainModel()
            }
        }
    }

    override suspend fun deleteAllAlarms() {
        alarmDao.deleteAllAlarms()
    }

    override suspend fun deleteAlarm(id: Int) {
        alarmDao.deleteAlarm(id)
    }

    override suspend fun saveSwitchState(position: Int, isChecked: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(position.toString())] = isChecked
        }
    }

    override suspend fun loadSwitchState(position: Int) : Boolean {
        val preferences = dataStore.data.first()
        return preferences[booleanPreferencesKey(position.toString())] ?: true
    }


}