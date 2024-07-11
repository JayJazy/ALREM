package com.jayys.alrem.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
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
object DataStoreModule {

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
}