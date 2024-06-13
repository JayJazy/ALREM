package com.jayys.alrem.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class DateListConverter {
    @TypeConverter
    fun fromTimestampList(value: String?): List<Date>? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<List<Date>>() {}.type
        return Gson().fromJson<List<Date>>(value, type)
    }

    @TypeConverter
    fun fromDateListToString(list: List<Date>?): String? {
        if (list == null) {
            return null
        }
        val type = object : TypeToken<List<Date>>() {}.type
        return Gson().toJson(list, type)
    }
}