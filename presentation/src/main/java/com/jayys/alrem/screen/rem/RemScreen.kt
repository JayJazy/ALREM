package com.jayys.alrem.screen.rem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.viemodel.AlarmDataViewModel


@Composable
fun RemScreen(
    itemValue : String,
    onNavigateToMainScreen: () -> Unit,
    alarmDataViewModel: AlarmDataViewModel = hiltViewModel(),
)
{
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit){
        alarmDataViewModel.getMaxId()
    }

    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner){
        val alarmError by alarmDataViewModel.error.collectAsStateWithLifecycle(lifecycleOwner.lifecycle)

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background))
        {
            if(alarmError != null){
                Text(text = "$alarmError\n앱을 다시 시작해 주세요", fontSize = 20.sp, color = Color.Black)
            }
            BoxWithConstraints {

                val screenHeight = maxHeight
                val statusBarHeightPx = WindowInsets.statusBars.getTop(LocalDensity.current)
                val statusBarHeightDp = with(LocalDensity.current) { statusBarHeightPx.toDp() }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = statusBarHeightDp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    RemImageLayout(screenHeight, itemValue, lifecycleOwner, onNavigateToMainScreen, alarmDataViewModel)
                }
            }
        }
    }

}