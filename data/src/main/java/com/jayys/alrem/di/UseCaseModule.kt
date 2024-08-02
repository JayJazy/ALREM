package com.jayys.alrem.di

import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.repository.SwitchStateRepository
import com.jayys.alrem.usecase.alarm.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetAllAlarmsUseCase(alarmRepository: AlarmRepository): GetAllAlarmsUseCase {
        return GetAllAlarmsUseCase(alarmRepository)
    }

    @Provides
    @Singleton
    fun providesSaveSwitchUseCase(switchStateRepository: SwitchStateRepository): SaveSwitchUseCase {
        return SaveSwitchUseCase(switchStateRepository)
    }
}