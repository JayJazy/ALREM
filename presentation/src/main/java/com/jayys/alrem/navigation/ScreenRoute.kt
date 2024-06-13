package com.jayys.alrem.navigation

import android.net.Uri
import com.google.gson.Gson

sealed class ScreenRoute(val route : String) {

    data object MainScreen : ScreenRoute("MainScreen")

    data object AlarmAddScreen : ScreenRoute("AlarmAddScreen/{settingData}"){
        fun createRoute(settingData : SettingData) : String{
            val json = Uri.encode(Gson().toJson(settingData))
            return "AlarmAddScreen/$json"
        }
    }

    data object MusicScreen : ScreenRoute("MusicScreen/{settingData}") {
        fun createRoute(settingData: SettingData): String {
            val json = Uri.encode(Gson().toJson(settingData))
            return "MusicScreen/$json"
        }
    }
}