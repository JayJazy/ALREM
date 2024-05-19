package com.jayys.alrem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jayys.alrem.navigation.ScreenNavHost
import com.jayys.alrem.ui.theme.ALREMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ALREMTheme {
                ScreenNavHost()
            }
        }
    }
}


