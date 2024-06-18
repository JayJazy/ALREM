package com.jayys.alrem.screen.alarmadd


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.screen.alarmadd.update.settingDataToUpdatedAlarmData
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel



@Composable
fun AlarmAddLayout(
    screenHeight: Dp,
    updateAlarmData: AlarmEntity,
    settingDataViewModel: SettingDataViewModel,
    alarmDataViewModel: AlarmDataViewModel,
    onNavigateToMainScreen: () -> Unit
) {
    AddButton(modifier = Modifier, text = "알 람 생 성", screenHeight = screenHeight)
    {
        if (settingDataViewModel.isUpdate)
        {
            val alarm = settingDataToUpdatedAlarmData(settingDataViewModel, updateAlarmData.id)
            alarmDataViewModel.updateAlarm(alarm)
            onNavigateToMainScreen()
        }
        else
        {
            val alarm = settingDataToUpdatedAlarmData(settingDataViewModel, 0)
            alarmDataViewModel.addAlarm(alarm)
            onNavigateToMainScreen()
        }
    }

}
