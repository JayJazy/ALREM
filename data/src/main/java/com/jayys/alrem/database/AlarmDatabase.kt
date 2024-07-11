package com.jayys.alrem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jayys.alrem.dao.AlarmDao
import com.jayys.alrem.dao.RemDao
import com.jayys.alrem.model.AlarmData
import com.jayys.alrem.converter.BooleanListConverter
import com.jayys.alrem.converter.DateConverter
import com.jayys.alrem.converter.DateListConverter
import com.jayys.alrem.converter.LocalDateTimeConverter
import com.jayys.alrem.model.RemData


@Database(entities = [AlarmData::class, RemData::class], version = 8, exportSchema = false)
@TypeConverters(DateConverter::class, DateListConverter::class, LocalDateTimeConverter::class, BooleanListConverter::class)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun AlarmDao() : AlarmDao

    abstract fun RemDao() : RemDao
}