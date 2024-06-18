package com.jayys.alrem.screen.alarmadd


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.component.AdvertisementLayout
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel

@Composable
fun AlarmAddScreen(
    updateAlarmData: AlarmEntity,
    settingData: SettingData,
    onNavigateToMusicScreen: (AlarmEntity, SettingData) -> Unit,
    onNavigateToMainScreen: () -> Unit,
    settingDataViewModel: SettingDataViewModel = hiltViewModel(),
    alarmDataViewModel: AlarmDataViewModel = hiltViewModel()
)
{
    settingDataViewModel.separateSettingData(settingData)


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background))
    {
        BoxWithConstraints {
            val screenHeight = maxHeight

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleLayout(screenHeight, settingDataViewModel)
                DayOfWeekLayout(screenHeight, settingDataViewModel)
                TimePickerLayout(screenHeight, settingDataViewModel)
                SettingItemsLayout(screenHeight, settingDataViewModel, updateAlarmData, onNavigateToMusicScreen)
                AlarmAddLayout(screenHeight, updateAlarmData, settingDataViewModel, alarmDataViewModel, onNavigateToMainScreen)
                Box(contentAlignment = Alignment.BottomCenter)
                {
                    AdvertisementLayout(screenHeight)
                }
            }
        }
    }

    BackHandler(enabled = true, onBack = {
        onNavigateToMainScreen()
    })
}