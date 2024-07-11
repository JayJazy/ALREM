package com.jayys.alrem.broadcastreceiver.alarmevent

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.jayys.alrem.entity.AlarmEntity
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


fun alarmBell(context : Context, alarm : AlarmEntity)
{
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val decodedUri = URLDecoder.decode(alarm.bellRingtone, StandardCharsets.UTF_8.toString())
    val ringtoneUri = Uri.parse(decodedUri)
    val ringtone = RingtoneManager.getRingtone(context, ringtoneUri)

    val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
    ringtone?.audioAttributes = audioAttributes

    audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarm.bellVolume, 0)
    ringtone?.play()

    if(alarm.repeat)
    {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            ringtone?.stop()
        }, (alarm.repeatMinute * 60 * 1000L) - 5000)
    }

}