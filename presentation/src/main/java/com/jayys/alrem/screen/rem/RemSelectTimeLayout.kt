package com.jayys.alrem.screen.rem

import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.jayys.alrem.R


@Composable
fun RemSelectTimeLayout(screenHeight: Dp, itemValue: String, onSettingsClick: (Int, Int) -> Unit)
{
    val defaultHour = if(itemValue == "sun") 7 else 22

    val title = if(itemValue == "sun") "일어나고 싶은 시간을 선택해 주세요"
    else "잠들고 싶은 시간을 선택해 주세요"


    var timepickerHour by remember { mutableIntStateOf(defaultHour) }
    var timepickerMinute by remember { mutableIntStateOf(30) }
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showText = true
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.5f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

            Spacer(modifier = Modifier.height(screenHeight * 0.5f * 0.05f))

            AnimatedVisibility(visible = showText,
                enter = fadeIn(animationSpec = tween(durationMillis = 3000))) {
                Text(text = title, color = Color.White, style = MaterialTheme.typography.bodyMedium, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.5f * 0.05f))


            AndroidView(factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.timepicker_layout, null, false)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                view.layoutParams = layoutParams
                val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
                timePicker.hour = timepickerHour
                timePicker.minute = timepickerMinute


                timePicker.setOnTimeChangedListener { _, hour, min ->
                    timepickerHour = hour
                    timepickerMinute = min
                }
                view
            }, modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.5f * 0.5f))

            Spacer(modifier = Modifier.height(screenHeight * 0.5f * 0.05f))

            TextButton(onClick = { onSettingsClick(timepickerHour, timepickerMinute) }, modifier = Modifier.height(screenHeight * 0.5f * 0.3f)){
                Text(text = "설 정", color = Color.White, modifier = Modifier.wrapContentSize(), fontSize = 16.sp)
            }
        }
    }

}