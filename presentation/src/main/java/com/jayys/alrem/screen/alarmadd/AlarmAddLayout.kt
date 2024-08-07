package com.jayys.alrem.screen.alarmadd


import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.screen.alarmadd.update.settingDataToUpdatedAlarmData
import com.jayys.alrem.utils.cancelExistingAlarm
import com.jayys.alrem.utils.setAlarm
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel


@Composable
fun AlarmAddLayout(
    screenHeight: Dp,
    updateAlarmData: AlarmEntity,
    lifecycleOwner: LifecycleOwner,
    settingDataViewModel: SettingDataViewModel,
    alarmDataViewModel: AlarmDataViewModel,
    switchViewModel: SwitchViewModel,
    onNavigateToMainScreen: () -> Unit
) {
    val maxId by alarmDataViewModel.maxId.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
    val context = LocalContext.current
    AddButton(modifier = Modifier, text = "알 람 생 성", screenHeight = screenHeight)
    {
        if (settingDataViewModel.isUpdate)
        {
            try
            {
                cancelExistingAlarm(updateAlarmData.id, context)

                val alarm = settingDataToUpdatedAlarmData(context, settingDataViewModel, updateAlarmData.id, true)

                switchViewModel.saveSwitchState(updateAlarmData.id, true)

                alarmDataViewModel.updateAlarm(alarm)

                setAlarm(alarm, context, true)
            }
            finally {
                Handler(Looper.getMainLooper()).postDelayed({
                    onNavigateToMainScreen()
                },200)
            }
        }
        else
        {
            try {
                val alarm = settingDataToUpdatedAlarmData(context, settingDataViewModel, maxId+1, true)

                switchViewModel.saveSwitchState(maxId+1, true)

                alarmDataViewModel.addAlarm(alarm)

                setAlarm(alarm, context, false)
            }
            finally {
                Handler(Looper.getMainLooper()).postDelayed({
                    onNavigateToMainScreen()
                },200)
            }
        }
    }
}



