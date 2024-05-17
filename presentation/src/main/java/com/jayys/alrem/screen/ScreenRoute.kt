package com.jayys.alrem.screen

sealed class ScreenRoute(val route : String) {

    data object MainScreen : ScreenRoute("MainScreen")
}