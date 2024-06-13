package com.jayys.alrem.repositoryImpl

import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.repository.AlarmRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao : AlarmDao
) : AlarmRepository