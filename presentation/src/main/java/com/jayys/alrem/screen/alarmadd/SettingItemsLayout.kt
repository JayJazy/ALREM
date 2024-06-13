package com.jayys.alrem.screen.alarmadd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.component.RepeatDialog
import com.jayys.alrem.component.SettingItem
import com.jayys.alrem.component.VolumeDialog
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.SettingDataViewModel


@Composable
fun SettingItemsLayout(
    screenHeight: Dp,
    settingDataViewModel: SettingDataViewModel,
    onNavigateToMusicScreen: (SettingData) -> Unit
)
{
    val settingItemText = listOf("음악", "진동", "시간 알람", "반복 시간", "수면 시간 기록")
    val switchStates = remember { mutableStateListOf(*settingDataViewModel.switchState.toTypedArray()) }

    var ttsVolumeDialog by remember { mutableStateOf(false) }

    var repeatDialog by remember { mutableStateOf(false) }

    val modifier = Modifier.padding(5.dp)

    if (ttsVolumeDialog) {
        VolumeDialog(
            initialValue = 0.134f,
            onDismiss =
            {
                ttsVolumeDialog = false
                switchStates[2] = false
                settingDataViewModel.switchState[2] = switchStates[2]
            },
            onConfirm = { newVolume ->
                settingDataViewModel.ttsVolume = (newVolume * 15).toInt()
                ttsVolumeDialog = false
            }
        )
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
                    0 -> { { onNavigateToMusicScreen(settingDataViewModel.createSettingData()) } }
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