package com.jayys.alrem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jayys.alrem.PermissionManager
import com.jayys.alrem.screen.alarmadd.AlarmAddScreen
import com.jayys.alrem.screen.main.MainScreen
import com.jayys.alrem.screen.music.MusicScreen

@Composable
fun ScreenNavHost(permissionManager: PermissionManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.MainScreen.route)
    {
        composable(route = ScreenRoute.MainScreen.route)
        {
            MainScreen(onNavigateToAlarmAddScreen = { settingData ->
                val route = ScreenRoute.AlarmAddScreen.createRoute(settingData)
                navController.navigate(route)
            })
        }

        composable(
            route = ScreenRoute.AlarmAddScreen.route,
            arguments = listOf(navArgument("settingData") { type = NavType.StringType })
        )
        { backStackEntry ->
            val settingDataJson = backStackEntry.arguments?.getString("settingData")
            val settingData = Gson().fromJson(settingDataJson, SettingData::class.java)

            AlarmAddScreen(settingData,
                onNavigateToMusicScreen = { settingDataToMusic ->
                    val route = ScreenRoute.MusicScreen.createRoute(settingDataToMusic)
                    navController.navigate(route) {
                        popUpTo(ScreenRoute.AlarmAddScreen.route) { inclusive = true }
                    }
                },
                onNavigateToMainScreen = {
                    navController.navigate(ScreenRoute.MainScreen.route) {
                        popUpTo(ScreenRoute.AlarmAddScreen.route) { inclusive = true }
                    }
                }
            )
        }


        composable(
            route = ScreenRoute.MusicScreen.route,
            arguments = listOf(navArgument("settingData") { type = NavType.StringType })
        )
        { backStackEntry ->
            val settingDataJson = backStackEntry.arguments?.getString("settingData")
            val settingData = Gson().fromJson(settingDataJson, SettingData::class.java)


            MusicScreen(permissionManager = permissionManager, settingData,
                onNavigateBackToAlarmAddScreen =
                {
                    updatedSettingData ->
                    val route = ScreenRoute.AlarmAddScreen.createRoute(updatedSettingData)
                    navController.navigate(route) { popUpTo(ScreenRoute.MusicScreen.route) { inclusive = true } }
                })
        }
    }
}