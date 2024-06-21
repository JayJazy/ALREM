package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.R


@Composable
fun RemButton(screenHeight: Dp, itemValue: String, onNavigateToRemScreen: (String) -> Unit)
{
    val iconPainter = when (itemValue) {
        "sun" -> painterResource(id = R.drawable.sun_icon)
        "moon" -> painterResource(id = R.drawable.moon_icon)
        else -> painterResource(id = R.drawable.ic_launcher_foreground)
    }

    val itemText = when(itemValue){
        "sun" -> "일어나고 싶은 시간이 있어요"
        "moon" -> "잠들고 싶은 시간이 있어요"
        else -> ""
    }



    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.28f * 0.25f)
        .padding(horizontal = 15.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable { onNavigateToRemScreen(itemValue) }
        .background(Color(0xFF393C41)),
        contentAlignment = Alignment.CenterStart
        )
    {
        Row(
            modifier = Modifier.padding(start = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = "아이콘 설명",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = itemText,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

        }
    }
    
   
}