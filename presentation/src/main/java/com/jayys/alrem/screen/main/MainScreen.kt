package com.jayys.alrem.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.component.AdvertisementLayout
import com.jayys.alrem.dialog.TimeOfSleepDialog
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.RemDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel
import com.jayys.alrem.viemodel.TimeOfSleepViewModel
import com.jayys.alrem.viemodel.WakeUpTimeViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    onNavigateToPreferencesScreen: () -> Unit,
    onNavigateToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit,
    onNavigateToRemScreen: (String) -> Unit,
    settingDataViewModel: SettingDataViewModel = hiltViewModel(),
    alarmDataViewModel: AlarmDataViewModel = hiltViewModel(),
    switchViewModel: SwitchViewModel = hiltViewModel(),
    wakeUpTimeViewModel: WakeUpTimeViewModel = hiltViewModel(),
    remDataViewModel: RemDataViewModel = hiltViewModel(),
    timeOfSleepViewModel: TimeOfSleepViewModel = hiltViewModel()
)
{
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit)
    {
        wakeUpTimeViewModel.loadWakeUpTime()
        remDataViewModel.getAllRems()
    }

    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner){
        val alarmError by alarmDataViewModel.error.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val remError by remDataViewModel.error.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val switchError by switchViewModel.error.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val wakeupError by wakeUpTimeViewModel.error.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)

        val alarmList by alarmDataViewModel.alarmData.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val switchStates by switchViewModel.switchStates.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val wakeUpTime by wakeUpTimeViewModel.wakeUpTime.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val remData by remDataViewModel.remData.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val timeOfSleep by timeOfSleepViewModel.timeOfSleep.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)

        LaunchedEffect(alarmList)
        {
            switchViewModel.loadSwitchStates(alarmList)
        }

        if (timeOfSleep) {
            TimeOfSleepDialog(onConfirm = { timeOfSleepViewModel.setTimeOfSleep(false) }, wakeUpTime, remDataViewModel)
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background))
        {
            BoxWithConstraints {
                val screenHeight = maxHeight
                val pagerState = rememberPagerState(initialPage = settingDataViewModel.pageNumber){2}
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopLayout(screenHeight, onNavigateToPreferencesScreen)
                    TopItemLayout(screenHeight, pagerState)
                    if(wakeupError != null){
                        Text(text = "$wakeupError\n앱을 다시 시작해 주세요", fontSize = 20.sp, color = Color.White)
                    }
                    AlarmAndRemLayout(
                        screenHeight,
                        alarmError,
                        switchError,
                        remError,
                        pagerState,
                        alarmList,
                        switchStates,
                        remData,
                        settingDataViewModel,
                        switchViewModel,
                        alarmDataViewModel,
                        onNavigateToAlarmAddScreen,
                        onNavigateToRemScreen)
                    Box(contentAlignment = Alignment.BottomCenter)
                    {
                        AdvertisementLayout(screenHeight)
                    }

                }
            }
        }

        val activity = LocalContext.current as Activity
        BackHandler(enabled = true, onBack = {
            activity.finish()
        })
    }
}