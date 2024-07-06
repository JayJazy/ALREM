package com.jayys.alrem.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.dao.RemDao
import com.jayys.alrem.database.AlarmDatabase
import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.usecase.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.SaveSwitchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private val Context.SwitchDataStore by preferencesDataStore(name = "Switch_pref")
private val Context.WakeUpDataStore by preferencesDataStore(name = "WakeUp_pref")
private val Context.OnBoardingDataStore by preferencesDataStore(name = "OnBoarding_pref")
private val Context.SleepOfTimeDataStore by preferencesDataStore(name = "SleepOfTime_pref")

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


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


    @Provides
    @Singleton
    @Named("SwitchDataStore")
    fun providesPreferencesSwitchDataStore(@ApplicationContext context: Context) = context.SwitchDataStore


    @Provides
    @Singleton
    @Named("WakeUpDataStore")
    fun providesPreferencesWakeUpDataStore(@ApplicationContext context: Context) = context.WakeUpDataStore


    @Provides
    @Singleton
    @Named("OnBoardingDataStore")
    fun providesPreferencesOnBoardingDataStore(@ApplicationContext context: Context) = context.OnBoardingDataStore

    @Provides
    @Singleton
    @Named("SleepOfTimeDataStore")
    fun providesPreferencesSleepOfTimeDataStore(@ApplicationContext context: Context) = context.SleepOfTimeDataStore


    @Provides
    @Singleton
    fun providesGetAllAlarmsUseCase(alarmRepository: AlarmRepository): GetAllAlarmsUseCase {
        return GetAllAlarmsUseCase(alarmRepository)
    }

    @Provides
    @Singleton
    fun providesSaveSwitchUseCase(alarmRepository: AlarmRepository): SaveSwitchUseCase {
        return SaveSwitchUseCase(alarmRepository)
    }

}