package com.jayys.alrem.screen.music

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.R
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.SettingDataViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private var tempRingtone: Ringtone? = null

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RingtonePlay(settingDataViewModel: SettingDataViewModel, pagerState: PagerState, onNavigateBackToAlarmAddScreen: (SettingData) -> Unit)
{
    val context = LocalContext.current
    val selectedUri by settingDataViewModel.selectedUri.collectAsStateWithLifecycle()
    val ringtoneName by settingDataViewModel.ringtoneName.collectAsStateWithLifecycle()

    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
    var sliderPosition by remember { mutableFloatStateOf(0.134f) }
    var isPlaying by remember { mutableStateOf(false) }

    val ringtone = RingtoneManager.getRingtone(context, selectedUri)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            isPlaying = false
            ringtoneStop()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(modifier = Modifier.weight(0.15f), contentAlignment = Alignment.Center){
                IconButton(
                    onClick =
                    {
                        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
                        ringtone?.audioAttributes = audioAttributes
                        isPlaying = !isPlaying
                        if (isPlaying) {
                            tempRingtone = ringtone
                            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (sliderPosition * maxVolume).toInt(), 0)
                            ringtone?.play()
                        }
                        else { ringtoneStop() }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 1.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (isPlaying) R.drawable.stop_icon else R.drawable.play_icon),
                        contentDescription = if (isPlaying) "Stop" else "Play",
                        tint = Color.Black
                    )
                }
            }

            Box(modifier = Modifier.weight(0.85f), contentAlignment = Alignment.CenterStart){
                Text(text = ringtoneName,
                    color = Color.Black,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyMedium)
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.15f), contentAlignment = Alignment.Center){
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.volume_icon),
                    contentDescription = "볼륨 아이콘",
                    tint = Color.DarkGray
                )
            }

            Box(modifier = Modifier
                .weight(0.85f)
                .padding(end = 10.dp), contentAlignment = Alignment.CenterStart){
                Slider(
                    value = sliderPosition,
                    onValueChange =
                    {
                        sliderPosition = if (it < 0.067f) 0.067f else it
                        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (sliderPosition * maxVolume).toInt(), 0)
                    },
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.secondary,
                        inactiveTrackColor = Color.White
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), contentAlignment = Alignment.Center){
            Button(onClick =
            {
                saveSettingData(settingDataViewModel, sliderPosition, maxVolume, pagerState)
                onNavigateBackToAlarmAddScreen(settingDataViewModel.createSettingData())
            },
                modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Text(text = "설 정", color = Color.Blue, fontSize = 18.sp)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                ringtoneStop()
            }
        }

    }
}

fun ringtoneStop()
{
    if (tempRingtone != null){
        tempRingtone!!.stop()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun saveSettingData(settingDataViewModel: SettingDataViewModel, sliderPosition: Float, maxVolume: Int, pagerState: PagerState) {
    settingDataViewModel.originalBellName = settingDataViewModel.bellName

    val uri = settingDataViewModel.selectedUri.value.toString()
    settingDataViewModel.ringtoneStringUri = URLEncoder.encode(uri, StandardCharsets.UTF_8.toString())

    settingDataViewModel.bellVolume = (sliderPosition * maxVolume).toInt()

    settingDataViewModel.pageNumber = pagerState.currentPage

}


