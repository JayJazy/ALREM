package com.jayys.alrem.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "Switch_pref")

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesUserDao(userDatabase: AlarmDatabase) : AlarmDao =
        userDatabase.AlarmDao()

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context) : AlarmDatabase =
        Room.databaseBuilder(context.applicationContext, AlarmDatabase::class.java, "user_table")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun providesPreferencesDataStore(@ApplicationContext context: Context) = context.dataStore
}