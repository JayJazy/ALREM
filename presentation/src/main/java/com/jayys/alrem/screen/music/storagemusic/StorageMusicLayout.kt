package com.jayys.alrem.screen.music.storagemusic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.music.RingtoneData
import com.jayys.alrem.screen.music.RingtoneItemView
import com.jayys.alrem.screen.music.RingtonePlayLayout
import com.jayys.alrem.viemodel.SettingDataViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageMusicLayout(
    settingDataViewModel: SettingDataViewModel,
    pagerState: PagerState,
    permissionManager: PermissionManager,
    updateAlarmData: AlarmEntity,
    onNavigateBackToAlarmAddScreen: (AlarmEntity, SettingData) -> Unit
) {
    val context = LocalContext.current
    var ringtoneList by remember { mutableStateOf<List<RingtoneData>>(emptyList()) }
    val selectedUri by settingDataViewModel.selectedUri.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        permissionManager.checkAndRequestStoragePermissions()
        ringtoneList = getStorageMusicList(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(
                items = ringtoneList,
                key = { it.contentUri.toString() }
            ) { item ->
                RingtoneItemView(item, settingDataViewModel) { uri ->
                    settingDataViewModel.setSelectedUri(uri)
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            }
        }

        if (selectedUri != null) {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        RingtonePlayLayout(
                            settingDataViewModel,
                            pagerState,
                            updateAlarmData,
                            onNavigateBackToAlarmAddScreen
                        )
                    }
                },
                sheetPeekHeight = 0.dp,
                content = {},
            )
        }
    }
}