
package com.jayys.alrem.screen.music

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.music.appmusic.AppMusicLayout
import com.jayys.alrem.screen.music.storagemusic.StorageMusicLayout
import com.jayys.alrem.screen.music.systemmusic.SystemMusicLayout
import com.jayys.alrem.viemodel.SettingDataViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicItemListLayout(
    screenHeight: Dp,
    pagerState: PagerState,
    permissionManager: PermissionManager,
    updateAlarmData: AlarmEntity,
    settingDataViewModel: SettingDataViewModel,
    onNavigateBackToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit
) {
    CompositionLocalProvider(LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current) {
        val lifecycleOwner = LocalLifecycleOwner.current
        settingDataViewModel.bellNameToRingtoneNameAsStateFlow()
        val ringtoneName by settingDataViewModel.ringtoneName.collectAsStateWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)

        MusicTopItemLayout(screenHeight = screenHeight, pagerState = pagerState)

        Box(
            modifier = Modifier
                .height(screenHeight * 0.07f)
                .fillMaxWidth()
                .padding(start = 15.dp), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "현재 벨소리 : $ringtoneName",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp,
                color = Color.White
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.77f)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
        )
        {
            HorizontalPager(state = pagerState, beyondBoundsPageCount = 3) { page ->
                when (page) {
                    0 -> AppMusicLayout(settingDataViewModel, pagerState, updateAlarmData, onNavigateBackToAlarmAddScreen)
                    1 -> SystemMusicLayout(settingDataViewModel, pagerState, updateAlarmData, onNavigateBackToAlarmAddScreen)
                    2 -> StorageMusicLayout(settingDataViewModel, pagerState, permissionManager, updateAlarmData, onNavigateBackToAlarmAddScreen)
                }
            }
        }
    }
}
