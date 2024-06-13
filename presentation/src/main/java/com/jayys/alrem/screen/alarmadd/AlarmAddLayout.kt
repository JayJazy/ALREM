package com.jayys.alrem.screen.alarmadd

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.viemodel.SettingDataViewModel

@Composable
fun AlarmAddLayout(
    screenHeight: Dp,
    settingDataViewModel: SettingDataViewModel,
    onNavigateToMainScreen: () -> Unit
)
{
    AddButton(modifier = Modifier, text = "알 람 생 성", screenHeight = screenHeight)
    {
        Log.d("TAG_VALUE", "${settingDataViewModel.createSettingData()}")
        onNavigateToMainScreen()
    }

}
