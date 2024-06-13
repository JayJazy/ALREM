package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun AdvertisementLayout(screenHeight: Dp)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.09f)
        .padding(top = 10.dp)
        .background(Color.LightGray))
    {
        Text(text = "광 고", color = Color.White)
    }
}