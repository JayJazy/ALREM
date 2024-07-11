package com.jayys.alrem.di

import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.repository.DataStoreRepository
import com.jayys.alrem.repository.RemRepository
import com.jayys.alrem.repositoryImpl.AlarmRepositoryImpl
import com.jayys.alrem.repositoryImpl.DataStoreRepositoryImpl
import com.jayys.alrem.repositoryImpl.RemRepositoryImpl
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
    abstract fun bindsDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl) : DataStoreRepository

}