package com.jayys.alrem.screen.rem

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.jayys.alrem.databinding.TimepickerLayoutBinding


@Composable
fun RemSelectTimeLayout(screenHeight: Dp, itemValue: String, onSettingsClick: (Int, Int) -> Unit)
{
    val defaultHour = if(itemValue == "sun") 7 else 22

    val title = if(itemValue == "sun") "일어나고 싶은 시간을 선택해 주세요\n\n잠들기 좋은 시간을 알려줄게요"
    else "잠들고 싶은 시간을 선택해 주세요\n\n일어나기 좋은 시간을 알려줄게요"


    var timepickerHour by remember { mutableIntStateOf(defaultHour) }
    var timepickerMinute by remember { mutableIntStateOf(30) }
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showText = true
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

            Spacer(modifier = Modifier.height(screenHeight * 0.15f))

            AnimatedVisibility(visible = showText,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000))) {
                Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.1f))

            AndroidView(
                factory = { context ->
                    val binding = TimepickerLayoutBinding.inflate(LayoutInflater.from(context), null, false)
                    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                    binding.root.layoutParams = layoutParams
                    binding.timePicker.apply {
                        hour = timepickerHour
                        minute = timepickerMinute

                        setOnTimeChangedListener { _, hour, min ->
                            timepickerHour = hour
                            timepickerMinute = min
                        }
                    }
                    binding.root
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.4f)
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.05f))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp).padding(vertical = 5.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .clickable { onSettingsClick(timepickerHour, timepickerMinute) },
                contentAlignment = Alignment.Center
            )
            {
                Text(text = "설 정", color = Color(0xFF51C0FF), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

        }
    }

}