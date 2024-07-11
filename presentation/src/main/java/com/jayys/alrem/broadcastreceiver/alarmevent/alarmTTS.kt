package com.jayys.alrem.broadcastreceiver.alarmevent

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import com.jayys.alrem.entity.AlarmEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private var tts: TextToSpeech? = null

fun alarmTTS(context : Context, alarm : AlarmEntity)
{
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH시 mm분")
    val currentTime = current.format(formatter)

    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    tts = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarm.ttsVolume, 0)
            tts?.setAudioAttributes(
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
            )
            tts?.speak("현재 시간은 $currentTime 입니다", TextToSpeech.QUEUE_FLUSH, null, null)
            tts?.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD, null)
        }
    }
}