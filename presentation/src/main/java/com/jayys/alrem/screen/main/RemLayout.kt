package com.jayys.alrem.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.component.RemButton
import com.jayys.alrem.viemodel.AlarmDataViewModel


@Composable
fun RemLayout(
    screenHeight: Dp,
    onNavigateToRemSelectScreen: (String) -> Unit
)
{
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.28f))
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(screenHeight * 0.28f * 0.1f))
                RemButton(screenHeight, "sun", onNavigateToRemSelectScreen)
                Spacer(modifier = Modifier.height(screenHeight * 0.28f * 0.15f))
                RemButton(screenHeight, "moon", onNavigateToRemSelectScreen)
                Spacer(modifier = Modifier.height(screenHeight * 0.28f * 0.1f))
            }
        }


        Box(modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.38f)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(MaterialTheme.colorScheme.onBackground))
        {

        }
    }
}