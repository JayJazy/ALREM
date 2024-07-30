package com.jayys.alrem.di

import android.content.Context
import androidx.room.Room
import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.dao.RemDao
import com.jayys.alrem.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesAlarmDao(alarmDatabase: AlarmDatabase) : AlarmDao =
        alarmDatabase.AlarmDao()


    @Provides
    @Singleton
    fun providesRemDao(remDatabase: AlarmDatabase) : RemDao =
        remDatabase.RemDao()



    @Provides
    @Singleton
    fun providesAlarmDatabase(@ApplicationContext context: Context) : AlarmDatabase =
        Room.databaseBuilder(context.applicationContext, AlarmDatabase::class.java, "ALARM_DATABASE")
            .fallbackToDestructiveMigration()
            .build()



}