package com.jayys.alrem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jayys.alrem.PermissionManager
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.screen.alarmadd.AlarmAddScreen
import com.jayys.alrem.screen.main.MainScreen
import com.jayys.alrem.screen.music.MusicScreen
import com.jayys.alrem.screen.rem.RemScreen

@Composable
fun ScreenNavHost(permissionManager: PermissionManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.MainScreen.route)
    {
        composable(route = ScreenRoute.MainScreen.route)
        {
            MainScreen(
                onNavigateToAlarmAddScreen = { updateAlarmData, settingData ->
                val route = ScreenRoute.AlarmAddScreen.createRoute(updateAlarmData, settingData)
                navController.navigate(route) },

                onNavigateToRemScreen = { itemValue ->
                    val route = ScreenRoute.RemScreen.createRoute(itemValue)
                    navController.navigate(route) })
        }

        composable(
            route = ScreenRoute.AlarmAddScreen.route,
            arguments = listOf(
                navArgument("updateAlarmData") { type = NavType.StringType },
                navArgument("settingData") { type = NavType.StringType }
            )
        )
        { backStackEntry ->
            val updateAlarmDataJson = backStackEntry.arguments?.getString("updateAlarmData")
            val settingDataJson = backStackEntry.arguments?.getString("settingData")

            val updateAlarmData = Gson().fromJson(updateAlarmDataJson, AlarmEntity::class.java)
            val settingData = Gson().fromJson(settingDataJson, SettingData::class.java)

            AlarmAddScreen(
                updateAlarmData = updateAlarmData,
                settingData = settingData,
                onNavigateToMusicScreen = { alarmData, settingDataToMusic ->
                    val route = ScreenRoute.MusicScreen.createRoute(alarmData, settingDataToMusic)
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
            arguments = listOf(
                navArgument("updateAlarmData") { type = NavType.StringType },
                navArgument("settingData") { type = NavType.StringType }
            )
        )
        { backStackEntry ->
            val updateAlarmDataJson = backStackEntry.arguments?.getString("updateAlarmData")
            val settingDataJson = backStackEntry.arguments?.getString("settingData")

            val updateAlarmData = Gson().fromJson(updateAlarmDataJson, AlarmEntity::class.java)
            val settingData = Gson().fromJson(settingDataJson, SettingData::class.java)


            MusicScreen(
                permissionManager = permissionManager,
                settingData = settingData,
                updateAlarmData = updateAlarmData,
                onNavigateBackToAlarmAddScreen =
                {
                    updatedAlarmData, updatedSettingData ->
                    val route = ScreenRoute.AlarmAddScreen.createRoute(updatedAlarmData, updatedSettingData)
                    navController.navigate(route) { popUpTo(ScreenRoute.MusicScreen.route) { inclusive = true } }
                })
        }


        composable(
            route = ScreenRoute.RemScreen.route,
            arguments = listOf( navArgument("itemValue") { type = NavType.StringType } )
        )
        {
            backStackEntry ->
            val itemValue = backStackEntry.arguments?.getString("itemValue")

            RemScreen(
                itemValue = itemValue ?: "",
                onNavigateToMainScreen = {
                    navController.navigate(ScreenRoute.MainScreen.route) { popUpTo(ScreenRoute.MusicScreen.route) { inclusive = true }}
                })
        }
    }
}