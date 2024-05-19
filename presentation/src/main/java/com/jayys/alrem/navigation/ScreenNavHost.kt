package com.jayys.alrem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jayys.alrem.screen.AlarmAddScreen
import com.jayys.alrem.screen.MainScreen

@Composable
fun ScreenNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.MainScreen.route)
    {
        composable(route = ScreenRoute.MainScreen.route)
        {
            MainScreen(onNavigateToAlarmAddScreen = { navController.navigate(ScreenRoute.AlarmAddScreen.route) })
        }

        composable(route = ScreenRoute.AlarmAddScreen.route)
        {
            AlarmAddScreen()
        }
    }
}