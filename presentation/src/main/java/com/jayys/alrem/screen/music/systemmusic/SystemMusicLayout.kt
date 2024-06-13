package com.jayys.alrem.screen.music.systemmusic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.navigation.SettingData
import com.jayys.alrem.screen.music.RingtoneItemView
import com.jayys.alrem.screen.music.RingtonePlay
import com.jayys.alrem.viemodel.SettingDataViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SystemMusicLayout(
    settingDataViewModel: SettingDataViewModel,
    pagerState: PagerState,
    onNavigateBackToAlarmAddScreen: (SettingData) -> Unit
)
{
    val context = LocalContext.current
    val ringtoneList = getSystemMusicList(context)
    val selectedUri by settingDataViewModel.selectedUri.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn {
            items(ringtoneList) { item ->
                RingtoneItemView(item, settingDataViewModel) { uri ->
                    settingDataViewModel.setSelectedUri(uri)
                    coroutineScope.launch{
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            }
        }

        if(selectedUri != null)
        {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent =
                {
                    Box(modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(), contentAlignment = Alignment.TopCenter){
                        RingtonePlay(settingDataViewModel, pagerState, onNavigateBackToAlarmAddScreen)
                    }
                },
                sheetPeekHeight = 0.dp,
                content = {},
            )
        }
    }
}