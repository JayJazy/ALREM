package com.jayys.alrem.screen

import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jayys.alrem.R
import com.jayys.alrem.component.AddButton
import com.jayys.alrem.component.SettingItem
import com.jayys.alrem.component.ToggleButton

@Composable
fun AlarmAddScreen()
{
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background))
    {
        BoxWithConstraints {
            val screenHeight = maxHeight

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleLayout(screenHeight)
                DayOfWeekLayout(screenHeight)
                TimePickerLayout(screenHeight)
                SettingItemsLayout(screenHeight)
                AlarmAddLayout(screenHeight)
                Box(contentAlignment = Alignment.BottomCenter)
                {
                    AdvertisementLayout2(screenHeight)
                }
            }
        }
    }
}


@Composable
fun TitleLayout(screenHeight : Dp)
{
    var alarmName by remember { mutableStateOf("") }

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
            value = alarmName,
            onValueChange = { alarmName = it },
            label = {
                Text("알람 제목", style = MaterialTheme.typography.bodyMedium, color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color.LightGray,
                focusedTextColor = Color.White
            ),
            textStyle = TextStyle(
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = FontWeight.Bold
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }

}


@Composable
fun DayOfWeekLayout(screenHeight: Dp)
{
    val weekdays = listOf("월", "화", "수", "목", "금", "토", "일")
    val toggleStates = remember { mutableStateListOf<Boolean>().apply { addAll(List(weekdays.size) { false }) } }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.08f)
        .padding(horizontal = 8.dp))
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            weekdays.forEachIndexed { index, day ->
                // 버튼 간 간격을 위한 패딩
                val modifier = Modifier.padding(horizontal = 4.dp)
                // 토글 상태에 따라 버튼 색상을 결정
                val buttonColor = if (toggleStates[index]) MaterialTheme.colorScheme.onPrimary else Color(0xFF4D4D4D)
                val textColor = when(index)
                {
                    5 -> Color.Blue
                    6 -> Color.Red
                    else -> Color.Black
                }
                ToggleButton(
                    day = day,
                    color = buttonColor,
                    textColor = textColor,
                    onClick = { toggleStates[index] = !toggleStates[index] },
                    modifier = modifier.size(35.dp)
                )
            }
        }
    }
}


@Composable
fun TimePickerLayout(screenHeight: Dp)
{
    val currentHour = remember { mutableStateOf(0) }
    val currentMin = remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.23f)
        .padding(top = 3.dp))
    {
        AndroidView(factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.timepicker_layout, null, false)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            view.layoutParams = layoutParams
            val timePicker = view.findViewById<TimePicker>(R.id.timePicker)

            currentHour.value = timePicker.hour
            currentMin.value = timePicker.minute

            timePicker.setOnTimeChangedListener { _, hourOfDay, min ->
                currentHour.value = timePicker.hour
                currentMin.value = timePicker.minute

            }


            view
        })
    }
}


@Composable
fun SettingItemsLayout(screenHeight: Dp)
{
    val settingItemText = listOf("음악", "진동", "시간 알람", "반복 시간", "수면 시간 기록")
    val modifier = Modifier.padding(5.dp)
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.35f)
        .padding(vertical = 10.dp))
    {
        LazyColumn{
            itemsIndexed(settingItemText) { index, settingText ->
                SettingItem(modifier = modifier, settingText = settingText, valueText = "5분", screenHeight)
            }
        }
    }
}


@Composable
fun AlarmAddLayout(screenHeight: Dp)
{
    AddButton(modifier = Modifier, text = "알 람 생 성", screenHeight = screenHeight)
}


@Composable
fun AdvertisementLayout2(screenHeight: Dp)
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

@Preview
@Composable
fun Preview()
{
    AlarmAddScreen()
}