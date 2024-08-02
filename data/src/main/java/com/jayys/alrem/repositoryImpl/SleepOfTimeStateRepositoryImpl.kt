package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jayys.alrem.repository.SleepOfTimeStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SleepOfTimeStateRepositoryImpl @Inject constructor(
    @Named("SleepOfTimeDataStore") private val sleepOfTimeDataStore: DataStore<Preferences>
) : SleepOfTimeStateRepository {

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