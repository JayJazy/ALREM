package com.jayys.alrem.navigation

import android.net.Uri
import com.google.gson.Gson
import com.jayys.alrem.entity.AlarmEntity

sealed class ScreenRoute(val route : String) {

    data object MainScreen : ScreenRoute("MainScreen")

    data object PreferencesScreen : ScreenRoute("PreferencesScreen")


    data object AlarmAddScreen : ScreenRoute("AlarmAddScreen/{updateAlarmData}/{settingData}"){
        fun createRoute(updateAlarmData : AlarmEntity,  settingData : SettingData) : String {
            val updateAlarmDataJson = Uri.encode(Gson().toJson(updateAlarmData))
            val settingDataJson = Uri.encode(Gson().toJson(settingData))
            return "AlarmAddScreen/$updateAlarmDataJson/$settingDataJson"
        }
    }

    data object MusicScreen : ScreenRoute("MusicScreen/{updateAlarmData}/{settingData}") {
        fun createRoute(updateAlarmData : AlarmEntity, settingData: SettingData): String {
            val updateAlarmDataJson = Uri.encode(Gson().toJson(updateAlarmData))
            val settingDataJson = Uri.encode(Gson().toJson(settingData))
            return "MusicScreen/$updateAlarmDataJson/$settingDataJson"
        }
    }

    data object RemScreen : ScreenRoute("RemScreen/{itemValue}"){
        fun createRoute(itemValue : String) : String{
            return "RemScreen/$itemValue"
        }
    }

}