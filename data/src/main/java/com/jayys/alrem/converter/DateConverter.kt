package com.jayys.alrem.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter
{
    @TypeConverter
    fun fromTimestampToDate(value : Long) : Date
    {
        return Date(value)
    }

    @TypeConverter
    fun fromDateToTimestamp(date : Date) : Long
    {
        return date.time
    }
}