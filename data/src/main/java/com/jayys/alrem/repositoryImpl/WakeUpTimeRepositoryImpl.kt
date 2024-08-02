package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.jayys.alrem.repository.WakeUpTimeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Named

class WakeUpTimeRepositoryImpl @Inject constructor(
    @Named("WakeUpDataStore") private val wakeUpDataStore: DataStore<Preferences>
) : WakeUpTimeRepository {


    override suspend fun saveWakeUpTime(wakeUpTime: LocalDateTime) {
        wakeUpDataStore.edit { preferences ->
            val wakeUpTimeInMillis = wakeUpTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            preferences[longPreferencesKey("wake_up_time")] = wakeUpTimeInMillis
        }
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

}