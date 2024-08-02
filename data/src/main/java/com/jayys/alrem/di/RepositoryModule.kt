package com.jayys.alrem.di

import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.repository.OnBoardingStateRepository
import com.jayys.alrem.repository.RemRepository
import com.jayys.alrem.repository.SleepOfTimeStateRepository
import com.jayys.alrem.repository.SwitchStateRepository
import com.jayys.alrem.repository.WakeUpTimeRepository
import com.jayys.alrem.repositoryImpl.AlarmRepositoryImpl
import com.jayys.alrem.repositoryImpl.OnBoardingStateRepositoryImpl
import com.jayys.alrem.repositoryImpl.RemRepositoryImpl
import com.jayys.alrem.repositoryImpl.SleepOfTimeStateRepositoryImpl
import com.jayys.alrem.repositoryImpl.SwitchStateRepositoryImpl
import com.jayys.alrem.repositoryImpl.WakeUpTimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl) : AlarmRepository

    @Singleton
    @Binds
    abstract fun bindsRemRepository(remRepositoryImpl: RemRepositoryImpl) : RemRepository


    @Singleton
    @Binds
    abstract fun bindsSwitchStateRepository(switchStateRepositoryImpl: SwitchStateRepositoryImpl) : SwitchStateRepository


    @Singleton
    @Binds
    abstract fun bindsOnBoardingStateRepository(onBoardingStateRepositoryImpl: OnBoardingStateRepositoryImpl) : OnBoardingStateRepository


    @Singleton
    @Binds
    abstract fun bindsWakeUpTimeRepository(wakeUpTimeRepositoryImpl: WakeUpTimeRepositoryImpl) : WakeUpTimeRepository

    @Singleton
    @Binds
    abstract fun bindsSleepOfTimeStateRepository(sleepOfTimeStateRepositoryImpl: SleepOfTimeStateRepositoryImpl) : SleepOfTimeStateRepository


}