package com.jayys.alrem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jayys.alrem.navigation.ScreenNavHost
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.ui.theme.ALREMTheme
import com.jayys.alrem.viemodel.OnBoardingViewModel
import com.jayys.alrem.viemodel.TimeOfSleepViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager
    private val timeOfSleepViewModel: TimeOfSleepViewModel by viewModels()
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            onBoardingViewModel.startDestination.value == null
        }


        val timeOfSleep = intent?.getBooleanExtra("TimeOfSleep", false) ?: false

        if(timeOfSleep)
        {
            timeOfSleepViewModel.setTimeOfSleep(true)
        }

        permissionManager = PermissionManager(this)

        setContent {
            ALREMTheme {
                val startDestination by onBoardingViewModel.startDestination
                startDestination?.let { ScreenNavHost(permissionManager, it) }
            }
        }
    }
}




