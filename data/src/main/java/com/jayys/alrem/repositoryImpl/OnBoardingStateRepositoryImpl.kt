package com.jayys.alrem.repositoryImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jayys.alrem.repository.OnBoardingStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class OnBoardingStateRepositoryImpl @Inject constructor(
    @Named("OnBoardingDataStore") private val onBoardingDataStore: DataStore<Preferences>
) : OnBoardingStateRepository {

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

}