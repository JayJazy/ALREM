package com.jayys.alrem.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.MainScreen.route)
    {
        composable(route = ScreenRoute.MainScreen.route)
        {
            MainScreen()
        }
    }
}