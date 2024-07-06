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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.alrem.viemodel.AlarmDataViewModel
import com.jayys.alrem.viemodel.SwitchViewModel


@Composable
fun RemScreen(
    itemValue : String,
    onNavigateToMainScreen: () -> Unit,
    alarmDataViewModel: AlarmDataViewModel = hiltViewModel(),
)
{
    val lifecycleOwner = LocalLifecycleOwner.current

    var currentLayout by remember { mutableStateOf("RemSelectTimeLayout") }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        alarmDataViewModel.getMaxId()
    }

    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background))
        {
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
                    RemImageLayout(screenHeight, itemValue)

                    when (currentLayout) {
                        "RemSelectTimeLayout" -> RemSelectTimeLayout(screenHeight, itemValue) { hour, minute ->
                            selectedHour = hour
                            selectedMinute = minute
                            currentLayout = "CalculateRemLayout"
                        }
                        "CalculateRemLayout" -> CalculateRemLayout(screenHeight, selectedHour, selectedMinute, itemValue, lifecycleOwner, onNavigateToMainScreen, alarmDataViewModel)
                    }
                }
            }
        }
    }

}