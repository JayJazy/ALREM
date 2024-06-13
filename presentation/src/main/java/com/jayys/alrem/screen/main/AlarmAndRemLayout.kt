package com.jayys.alrem.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.SettingDataViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmAndRemLayout(
    screenHeight: Dp,
    pagerState: PagerState,
    onNavigateToAlarmAddScreen: (SettingData) -> Unit,
    settingDataViewModel: SettingDataViewModel
)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.66f))
    {
        HorizontalPager(state = pagerState, beyondBoundsPageCount = 2) { page ->
            when (page) {
                0 -> AlarmListLayout(screenHeight, onNavigateToAlarmAddScreen, settingDataViewModel)
                1 -> RemLayout()
            }
        }
    }
}