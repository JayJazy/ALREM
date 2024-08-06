package com.jayys.alrem.screen.rem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LifecycleOwner
import com.jayys.alrem.R
import com.jayys.alrem.viemodel.AlarmDataViewModel


@Composable
fun RemImageLayout(
    screenHeight: Dp,
    itemValue: String,
    lifecycleOwner: LifecycleOwner,
    onNavigateToMainScreen: () -> Unit,
    alarmDataViewModel: AlarmDataViewModel
)
{
    val imageResId = if(itemValue == "sun") R.drawable.morning
    else R.drawable.night

    val imageDescription = if(itemValue == "sun") stringResource(id = R.string.description_morning_image)
    else stringResource(id = R.string.description_night_image)

    var currentLayout by remember { mutableStateOf("RemSelectTimeLayout") }
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }


    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = imageDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.4f)
        )


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