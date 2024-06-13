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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.component.AdvertisementLayout
import com.jayys.alrem.viemodel.SettingDataViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    onNavigateToAlarmAddScreen : (SettingData) -> Unit,
    settingDataViewModel: SettingDataViewModel = hiltViewModel()
)
{
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
                TopLayout(screenHeight)
                TopItemLayout(screenHeight, pagerState)
                AlarmAndRemLayout(screenHeight, pagerState, onNavigateToAlarmAddScreen, settingDataViewModel)
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