package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.jayys.alrem.repository.DataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    @Named("SwitchDataStore") private val switchDataStore: DataStore<Preferences>,
    @Named("WakeUpDataStore") private val wakeUpDataStore: DataStore<Preferences>,
    @Named("OnBoardingDataStore") private val onBoardingDataStore: DataStore<Preferences>,
    @Named("SleepOfTimeDataStore") private val sleepOfTimeDataStore: DataStore<Preferences>
) : DataStoreRepository {

    private val switchStatesCache = mutableMapOf<Int, Boolean>()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            switchDataStore.data.collect { preferences ->
                preferences.asMap().forEach { (key, value) ->
                    if (value is Boolean) {
                        switchStatesCache[key.name.toInt()] = value
                    }
                }
            }
        }
    }

    override suspend fun saveSwitchState(position: Int, isChecked: Boolean) {
        switchDataStore.edit { preferences ->
            preferences[booleanPreferencesKey(position.toString())] = isChecked
        }
        switchStatesCache[position] = isChecked
    }

    override suspend fun loadSwitchState(position: Int) : Boolean {
        return switchStatesCache[position] ?: run {
            val preferences = switchDataStore.data.first()
            preferences[booleanPreferencesKey(position.toString())] ?: true
        }
    }


    override suspend fun saveWakeUpTime(wakeUpTime: LocalDateTime) {
        wakeUpDataStore.edit { preferences ->
            val wakeUpTimeInMillis = wakeUpTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            preferences[longPreferencesKey("wake_up_time")] = wakeUpTimeInMillis
        }
    }

    override suspend fun loadAllSwitchStates(): Map<Int, Boolean> {
        return switchStatesCache.toMap()
    }

    override suspend fun loadWakeUpTime(): LocalDateTime {
        val wakeUpTimeInMillis = wakeUpDataStore.data
            .map { preferences ->
                preferences[longPreferencesKey("wake_up_time")]
            }.first()

        return if (wakeUpTimeInMillis != null) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(wakeUpTimeInMillis), ZoneOffset.UTC)
        } else {
            LocalDateTime.now().withSecond(0).withNano(0)
        }
    }


    override suspend fun saveOnBoardingState(state: Boolean) {
        onBoardingDataStore.edit { preferences ->
            preferences[booleanPreferencesKey("OnBoarding")] = state
        }
    }

    override fun loadOnBoardingState(): Flow<Boolean> {
        return onBoardingDataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("OnBoarding")] ?: false
        }
    }


    override suspend fun saveSleepOfTimeState(state: Boolean) {
        sleepOfTimeDataStore.edit { preferences ->
            preferences[booleanPreferencesKey("sleepOfTime")] = state
        }
    }

    override fun loadSleepOfTimeState(): Flow<Boolean> {
        return sleepOfTimeDataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("sleepOfTime")] ?: false
        }
    }
}