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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity


@Composable
fun RemScreen(itemValue : String, onNavigateToMainScreen: () -> Unit)
{
    var currentLayout by remember { mutableStateOf("RemSelectTimeLayout") }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }


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
                    "CalculateRemLayout" -> CalculateRemLayout(screenHeight, selectedHour, selectedMinute, itemValue, onNavigateToMainScreen)
                }
            }
        }
    }
}