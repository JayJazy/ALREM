package com.jayys.alrem.broadcastreceiver.alarmevent

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

fun alarmVibration(context : Context)
{
    // 진동 타이밍 : 1.3초 쉬고 -> 1.5초 진동 * 16번
    val timing = longArrayOf(
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,
        1300, 1500, 1300, 1500,)

    // 진동 세기 : 1.3초 쉴때 0 -> 1.5초 진동 세기 : 200
    val amp = intArrayOf(
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,
        0, 200, 0, 200,)


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vibrator = vibratorManager.defaultVibrator
        val vibrationEffect = VibrationEffect.createWaveform(timing, amp, -1)
        vibrator.vibrate(vibrationEffect)
    }
    else
    {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createWaveform(timing, amp, -1)
            vibrator.vibrate(vibrationEffect)
        }
        else
        {
            vibrator.vibrate(timing, 15)
        }
    }
}
