package com.jayys.alrem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jayys.alrem.navigation.ScreenNavHost
import com.jayys.alrem.ui.theme.ALREMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        permissionManager = PermissionManager(this)

        setContent {
            ALREMTheme {
                ScreenNavHost(permissionManager)
            }
        }
    }
}




