package com.jayys.alrem.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.rem.RemLayout
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SettingDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmAndRemLayout(
    screenHeight: Dp,
    alarmError: String?,
    switchError: String?,
    remError: String?,
    pagerState: PagerState,
    alarmList: List<AlarmEntity>,
    switchStates: Map<Int, Boolean>,
    remData: List<RemEntity>,
    settingDataViewModel: SettingDataViewModel,
    switchViewModel: SwitchViewModel,
    alarmDataViewModel: AlarmDataViewModel,
    onNavigateToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit,
    onNavigateToRemScreen: (String) -> Unit,
)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.66f))
    {
        HorizontalPager(state = pagerState, beyondBoundsPageCount = 2) { page ->
            when (page) {
                0 -> AlarmListLayout(
                    screenHeight,
                    alarmError,
                    switchError,
                    alarmList,
                    switchStates,
                    settingDataViewModel,
                    switchViewModel,
                    alarmDataViewModel,
                    onNavigateToAlarmAddScreen)
                1 -> RemLayout(screenHeight, pagerState, remError, remData, onNavigateToRemScreen)
            }
        }
    }
}