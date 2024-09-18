package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jayys.alrem.repository.SwitchStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Named

class SwitchStateRepositoryImpl @Inject constructor(
    @Named("SwitchDataStore") private val switchDataStore: DataStore<Preferences>
) : SwitchStateRepository {

    private val switchStatesCache = mutableMapOf<Int, Boolean>()
    private val mutex = Mutex()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        runBlocking {
            loadInitialData()
        }

        scope.launch {
            switchDataStore.data.collect { preferences ->
                mutex.withLock {
                    preferences.asMap().forEach { (key, value) ->
                        if (value is Boolean) {
                            switchStatesCache[key.name.toInt()] = value
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadInitialData() {
        val preferences = switchDataStore.data.first()
        mutex.withLock {
            preferences.asMap().forEach { (key, value) ->
                if (value is Boolean) {
                    switchStatesCache[key.name.toInt()] = value
                }
            }
        }
    }

    override suspend fun saveSwitchState(position: Int, isChecked: Boolean) {
        switchDataStore.edit { preferences ->
            preferences[booleanPreferencesKey(position.toString())] = isChecked
        }
        mutex.withLock {
            switchStatesCache[position] = isChecked
        }
    }

    override suspend fun loadSwitchState(position: Int): Boolean {
        return mutex.withLock {
            switchStatesCache[position] ?: true
        }
    }

    override suspend fun loadAllSwitchStates(): Map<Int, Boolean> {
        return mutex.withLock {
            switchStatesCache.toMap()
        }
    }
}