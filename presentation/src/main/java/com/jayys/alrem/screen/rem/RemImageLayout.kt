package com.jayys.alrem.screen.rem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.R


@Composable
fun RemImageLayout(screenHeight: Dp, itemValue: String)
{
    val morningImageResIds = listOf(
        R.drawable.morning1,
        R.drawable.morning2,
        R.drawable.morning3
    )

    val nightImageResIds = listOf(
        R.drawable.night1,
        R.drawable.night2
    )

    val randomImageResId = if(itemValue == "sun") morningImageResIds.random()
    else nightImageResIds.random()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.5f)
    ) {
        Image(
            painter = painterResource(id = randomImageResId),
            contentDescription = "사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.5f * 0.1f)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)
        )
    }

}