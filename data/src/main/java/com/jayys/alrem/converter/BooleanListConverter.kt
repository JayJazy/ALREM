package com.jayys.alrem.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class BooleanListConverter {
    @TypeConverter
    fun fromBooleanList(value: List<Boolean>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toBooleanList(value: String?): List<Boolean>? {
        val listType = object : TypeToken<List<Boolean>>() {}.type
        return Gson().fromJson(value, listType)
    }
}