package com.jayys.alrem.destination

import android.content.Context

fun clearCurrentAlarm(context: Context) {
    val sharedPref = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        remove("currentAlarm")
        apply()
    }
}