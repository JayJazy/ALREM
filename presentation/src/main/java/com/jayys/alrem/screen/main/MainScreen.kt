package com.jayys.alrem.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.component.AdvertisementLayout
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    onNavigateToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit,
    settingDataViewModel: SettingDataViewModel = hiltViewModel(),
    alarmDataViewModel: AlarmDataViewModel = hiltViewModel(),
    switchViewModel: SwitchViewModel = hiltViewModel(),
)
{
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        alarmDataViewModel.getAllAlarms()
    }


    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner){
        val alarmList by alarmDataViewModel.alarmData.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        val switchStates by switchViewModel.switchStates.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)
        LaunchedEffect(alarmList)
        {
            switchViewModel.loadSwitchStates(alarmList)
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
                    TopLayout(screenHeight, alarmDataViewModel)
                    TopItemLayout(screenHeight, pagerState)
                    AlarmAndRemLayout(
                        screenHeight,
                        pagerState,
                        alarmList,
                        switchStates,
                        settingDataViewModel,
                        switchViewModel,
                        alarmDataViewModel,
                        onNavigateToAlarmAddScreen)
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