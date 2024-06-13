package com.jayys.alrem.screen.music

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jayys.alrem.PermissionManager
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.viemodel.SettingDataViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicScreen(
    permissionManager : PermissionManager,
    settingData: SettingData,
    onNavigateBackToAlarmAddScreen: (SettingData) -> Unit,
    settingDataViewModel: SettingDataViewModel = hiltViewModel()
) {
    settingDataViewModel.separateSettingData(settingData)
    settingDataViewModel.originalBellName = settingDataViewModel.bellName

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background))
    {
        BoxWithConstraints {
            val screenHeight = maxHeight

            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val pagerState = rememberPagerState(initialPage = settingDataViewModel.pageNumber){3}

                MusicItemListLayout(screenHeight, pagerState, permissionManager, settingDataViewModel, onNavigateBackToAlarmAddScreen)

            }
        }
    }

    BackHandler(enabled = true, onBack = {
        settingDataViewModel.bellName = settingDataViewModel.originalBellName
        onNavigateBackToAlarmAddScreen(settingDataViewModel.createSettingData())
    })
}
