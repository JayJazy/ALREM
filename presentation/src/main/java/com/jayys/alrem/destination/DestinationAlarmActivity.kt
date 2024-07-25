package com.jayys.alrem.destination


import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.gson.Gson
import com.jayys.alrem.destination.dismiss.DismissAlarm
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import com.jayys.alrem.usecase.datastore.SaveWakeUpTimeUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DestinationAlarmActivity : ComponentActivity() {

    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    @Inject
    lateinit var dismissAlarm : DismissAlarm

    @Inject
    lateinit var saveSwitchUseCase : SaveSwitchUseCase

    @Inject
    lateinit var saveWakeUpTimeUseCase : SaveWakeUpTimeUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val alarmJson = intent.getStringExtra("alarm")
        val gson = Gson()
        val alarm: AlarmEntity = gson.fromJson(alarmJson, AlarmEntity::class.java)

        setContent {
            DestinationLayout(alarm, dismissAlarm, saveSwitchUseCase, saveWakeUpTimeUseCase)
        }
    }
}

