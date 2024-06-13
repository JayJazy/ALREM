package com.jayys.alrem.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.SettingDataViewModel


@Composable
fun AlarmListLayout(
    screenHeight: Dp,
    onNavigateToAlarmAddScreen: (SettingData) -> Unit,
    settingDataViewModel: SettingDataViewModel
)
{
    Column {
        Box(modifier = Modifier.fillMaxWidth().height(screenHeight * 0.58f))
        {
            Text(text = "알람 리스트", color = Color.White)
        }

        AddButton(
            modifier = Modifier,
            text = "알 람 추 가", screenHeight = screenHeight
        ) { onNavigateToAlarmAddScreen(settingDataViewModel.createSettingData()) }

    }
}