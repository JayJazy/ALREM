package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.jayys.alrem.R
import kotlinx.coroutines.delay


@Composable
fun SettingItem(
    modifier: Modifier,
    settingText: String,
    valueText: String,
    screenHeight: Dp,
    onClick: () -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showRemButton : Boolean
)
{
    var showPopup by remember { mutableStateOf(false) }
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .clip(RoundedCornerShape(8.dp))
        .height(screenHeight * 0.13f * 0.59f)
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.primary))
    {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = modifier
                .weight(1.1f)
                .padding(start = 15.dp), contentAlignment = Alignment.CenterStart){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = settingText, color = Color.White, fontFamily = MaterialTheme.typography.bodyMedium.fontFamily)
                }

            }

            Box(modifier = modifier.weight(1.3f), contentAlignment = Alignment.CenterStart){
                if (showRemButton) {
                    IconButton(
                        onClick = { showPopup = true},
                        modifier = Modifier.padding(top = 1.dp).size(23.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.help_icon),
                            contentDescription = "rem 수면 기록 안내",
                            tint = Color.Gray
                        )
                    }
                }
                Text(text = valueText, color = MaterialTheme.colorScheme.onPrimary, fontFamily = MaterialTheme.typography.bodyMedium.fontFamily)
            }

            Box(modifier = modifier.weight(0.7f), contentAlignment = Alignment.CenterStart){
                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.onSecondary,
                        uncheckedBorderColor = Color.Gray,
                        uncheckedTrackColor = Color.Transparent
                    ),
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }

        }
    }




    if(showPopup)
    {
        LaunchedEffect(Unit) {
            delay(3000) // 3초 후에 팝업을 닫음
            showPopup = false
        }
        Popup(
            alignment = Alignment.BottomCenter,
            onDismissRequest = { showPopup = false }
        ) {
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
                    .background(Color(0xFFFFEEFE), shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "알람을 끄고 잠든 시간을 설정하세요\n" +
                        "설정이 완료되면 수면 시간이 기록돼요",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp))
            }
        }
    }
}
