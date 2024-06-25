package com.jayys.alrem.screen.alarmadd

import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.dialog.RepeatDialog
import com.jayys.alrem.component.SettingItem
import com.jayys.alrem.dialog.VolumeDialog
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.alarmadd.update.settingDataToUpdatedAlarmData
import com.jayys.alrem.viemodel.SettingDataViewModel


@Composable
fun SettingItemsLayout(
    screenHeight: Dp,
    settingDataViewModel: SettingDataViewModel,
    updateAlarmData: AlarmEntity,
    onNavigateToMusicScreen: (AlarmEntity, SettingData) -> Unit
)
{
    val context = LocalContext.current
    val settingItemText = listOf("음악", "진동", "시간 알람", "반복 시간", "수면 시간 기록")
    val switchStates = remember { mutableStateListOf(*settingDataViewModel.switchState.toTypedArray()) }

    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
    var ttsVolumeDialog by remember { mutableStateOf(false) }
    var volumeValue by remember { mutableFloatStateOf( settingDataViewModel.ttsVolume.toFloat() / maxVolume.toFloat() ) }

    var repeatDialog by remember { mutableStateOf(false) }

    val modifier = Modifier.padding(5.dp)

    if (ttsVolumeDialog) {
        VolumeDialog(
            initialValue = volumeValue,
            maxVolume = maxVolume,
            onDismiss =
            {
                ttsVolumeDialog = false
                switchStates[2] = false
                settingDataViewModel.switchState[2] = switchStates[2]
            }
        )
        { newVolume ->
            settingDataViewModel.ttsVolume = (newVolume * maxVolume).toInt()
            volumeValue = settingDataViewModel.ttsVolume.toFloat() / maxVolume.toFloat()
            ttsVolumeDialog = false
        }
    }

    if(repeatDialog)
    {
        RepeatDialog(
            initialValue = 5,
            onDismiss =
            {
                repeatDialog = false
                switchStates[3] = false
                settingDataViewModel.switchState[3] = switchStates[3]
            },
            onConfirm = { min ->
                settingDataViewModel.repeatMinute = min
                repeatDialog = false
            }
        )
    }


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.35f)
        .padding(vertical = 10.dp))
    {
        LazyColumn{
            itemsIndexed(settingItemText) { index, settingText ->
                val valueText = when(index){
                    0 -> if (switchStates[index]) settingDataViewModel.bellName else ""
                    3 -> if (switchStates[index]) "${settingDataViewModel.repeatMinute}분" else ""
                    else -> ""
                }

                val onClick: () -> Unit = when (index) {
                    0 -> { {
                            if (settingDataViewModel.isUpdate)
                            {
                                val alarm = settingDataToUpdatedAlarmData(settingDataViewModel, updateAlarmData.id)
                                onNavigateToMusicScreen(alarm, settingDataViewModel.createSettingData())
                            }
                            else
                            {
                                val alarm = settingDataToUpdatedAlarmData(settingDataViewModel, 0)
                                onNavigateToMusicScreen(alarm, settingDataViewModel.createSettingData())
                            }

                        } }
                    else -> { { } }
                }

                val onCheckedChange: (Boolean) -> Unit = { isChecked ->
                    switchStates[index] = isChecked
                    settingDataViewModel.switchState[index] = switchStates[index]
                    when (index) {
                        2 -> if (isChecked) ttsVolumeDialog = true
                        3 -> if (isChecked) repeatDialog = true
                    }
                }

                SettingItem(modifier, settingText, valueText, screenHeight, onClick, switchStates[index], onCheckedChange, index == settingItemText.lastIndex)
            }
        }
    }
}