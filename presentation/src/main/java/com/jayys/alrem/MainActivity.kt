package com.jayys.alrem

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jayys.alrem.navigation.ScreenNavHost
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.ui.theme.ALREMTheme
import com.jayys.alrem.viemodel.TimeOfSleepViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager
    private val timeOfSleepViewModel: TimeOfSleepViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val timeOfSleep = intent?.getBooleanExtra("TimeOfSleep", false) ?: false

        if(timeOfSleep)
        {
            timeOfSleepViewModel.setTimeOfSleep(true)
        }

        permissionManager = PermissionManager(this)

        setContent {
            ALREMTheme {
                ScreenNavHost(permissionManager)
            }
        }
    }
}




