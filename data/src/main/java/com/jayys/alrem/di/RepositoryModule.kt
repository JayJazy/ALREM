package com.jayys.alrem.di

import com.jayys.alrem.repository.AlarmRepository
import com.jayys.alrem.repositoryImpl.AlarmRepositoryImpl
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
    abstract fun bindsUserRepository(userRepositoryImpl: AlarmRepositoryImpl) : AlarmRepository
}