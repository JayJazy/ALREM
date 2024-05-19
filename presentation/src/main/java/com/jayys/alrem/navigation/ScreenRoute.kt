package com.jayys.alrem.navigation

sealed class ScreenRoute(val route : String) {

    data object MainScreen : ScreenRoute("MainScreen")

    data object AlarmAddScreen : ScreenRoute("AlarmAddScreen")
}