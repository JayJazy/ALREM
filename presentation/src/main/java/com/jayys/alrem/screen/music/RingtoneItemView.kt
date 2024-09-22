package com.jayys.alrem.screen.music

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.viemodel.SettingDataViewModel


@Composable
fun RingtoneItemView(
    item: RingtoneData,
    settingDataViewModel: SettingDataViewModel,
    onItemSelect: (Uri) -> Unit
)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(5.dp)
        .clickable
        {
            onItemSelect(item.contentUri)

            settingDataViewModel.setSelectedUri(item.contentUri)

            settingDataViewModel.bellName = item.title
            settingDataViewModel.bellNameToRingtoneNameAsStateFlow()
        }, contentAlignment = Alignment.CenterStart)
    {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center)
        {
            RadioButton(
                selected = settingDataViewModel.bellName == item.title,
                onClick =
                {
                    onItemSelect(item.contentUri)

                    settingDataViewModel.setSelectedUri(item.contentUri)

                    settingDataViewModel.bellName = item.title
                    settingDataViewModel.bellNameToRingtoneNameAsStateFlow()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFE494FF),
                ),
                modifier = Modifier.semantics {
                    contentDescription = "${item.title} ${if (settingDataViewModel.bellName == item.title) "선택됨" else "선택되지 않음"}"
                }
            )
            Text(text = item.title,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp).semantics(mergeDescendants = true) {},
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}