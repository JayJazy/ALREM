package com.jayys.alrem.screen.alarmadd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.viemodel.SettingDataViewModel

@Composable
fun TitleLayout(screenHeight: Dp, settingDataViewModel: SettingDataViewModel)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 45.dp)
        .height(screenHeight * 0.1f))
    {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .padding(horizontal = 20.dp),
            value = settingDataViewModel.alarmName,
            onValueChange =
            {
                if (it.length <= 20) { settingDataViewModel.alarmName = it }
            },
            label = {
                Text("알람 제목", style = MaterialTheme.typography.bodyMedium, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            textStyle = TextStyle(
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = FontWeight.Bold
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}