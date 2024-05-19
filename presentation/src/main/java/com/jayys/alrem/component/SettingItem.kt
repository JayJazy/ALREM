package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SettingItem(modifier : Modifier, settingText : String, valueText : String, screenHeight : Dp)
{
    var isChecked by remember { mutableStateOf(false) }
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .clip(RoundedCornerShape(8.dp))
        .height(screenHeight * 0.13f * 0.59f)
        .background(MaterialTheme.colorScheme.primary))
    {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = modifier.weight(1.1f).padding(start = 15.dp), contentAlignment = Alignment.CenterStart){
                Text(text = settingText, color = Color.White, fontFamily = MaterialTheme.typography.bodyMedium.fontFamily)
            }

            Box(modifier = modifier.weight(1.3f), contentAlignment = Alignment.CenterStart){
                Text(text = valueText, color = MaterialTheme.colorScheme.onPrimary, fontFamily = MaterialTheme.typography.bodyMedium.fontFamily)
            }

            Box(modifier = modifier.weight(0.7f), contentAlignment = Alignment.CenterStart){
                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFC847F4),
                        uncheckedBorderColor = Color.Gray,
                        uncheckedTrackColor = Color.Transparent
                    ),
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
            }

        }
    }
}