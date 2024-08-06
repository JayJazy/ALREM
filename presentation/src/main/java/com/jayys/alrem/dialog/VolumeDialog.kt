package com.jayys.alrem.dialog

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jayys.alrem.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private var tts : TextToSpeech? = null

@Composable
fun VolumeDialog(
    initialValue: Float,
    maxVolume: Int,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    val context = LocalContext.current
    var sliderPosition by remember { mutableFloatStateOf(initialValue) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "시간 알람 볼륨을 설정 하세요", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick =
                        {
                            tts?.stop()
                            ttsPlay(context, sliderPosition, maxVolume)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.play_icon),
                            contentDescription = stringResource(id = R.string.description_play_button),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = if (it < 0.067f) 0.067f else it  },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.secondary,
                            activeTrackColor = MaterialTheme.colorScheme.secondary,
                            inactiveTrackColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("취소", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirm(sliderPosition) }) {
                        Text("확인", color = Color.White)
                    }
                }
            }
        }
    }
}

fun ttsPlay(context: Context, sliderPosition: Float, maxVolume: Int) {
    val newVolume = sliderPosition * maxVolume

    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH시 mm분")
    val currentTime = current.format(formatter)

    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    tts = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            audioManager.setStreamVolume(
                AudioManager.STREAM_ALARM, (audioManager.getStreamMaxVolume(
                    AudioManager.STREAM_ALARM
                ) * newVolume.toInt() / maxVolume), 0
            )
            tts?.setAudioAttributes(
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
            )
            tts?.speak("현재 시간은 $currentTime 입니다", TextToSpeech.QUEUE_FLUSH, null, null)
            tts?.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD, null)
        }
    }
}
