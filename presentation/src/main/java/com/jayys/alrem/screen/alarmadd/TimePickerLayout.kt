package com.jayys.alrem.screen.alarmadd

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jayys.alrem.databinding.TimepickerLayoutBinding
import com.jayys.alrem.viemodel.SettingDataViewModel


@Composable
fun TimePickerLayout(screenHeight: Dp, settingDataViewModel: SettingDataViewModel)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.23f)
        .padding(top = 3.dp))
    {
        AndroidView(factory = { context ->
            val binding = TimepickerLayoutBinding.inflate(LayoutInflater.from(context), null, false)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            binding.root.layoutParams = layoutParams

            binding.timePicker.apply {
                hour = settingDataViewModel.hour
                minute = settingDataViewModel.min

                setOnTimeChangedListener { _, hour, min ->
                    settingDataViewModel.hour = hour
                    settingDataViewModel.min = min
                }
            }
            binding.root
        })
    }
}
