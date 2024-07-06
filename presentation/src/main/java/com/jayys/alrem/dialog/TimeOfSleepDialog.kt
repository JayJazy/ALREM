package com.jayys.alrem.dialog


import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jayys.alrem.R
import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.utils.calculateSleepTime
import com.jayys.alrem.viemodel.RemDataViewModel
import java.time.LocalDateTime


@Composable
fun TimeOfSleepDialog(
    onConfirm: (Boolean) -> Unit,
    wakeUpTime: LocalDateTime,
    remDataViewModel: RemDataViewModel
)
{
    var selectedHour by remember { mutableIntStateOf(22) }
    var selectedMin by remember { mutableIntStateOf(30) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var shouldSaveRemData by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {

        val bedTimeHourText = if(selectedHour > 12) "오후 ${selectedHour - 12}시"
        else if(selectedHour == 12)  "오후 ${selectedHour}시"
            else "오전 ${selectedHour}시"
        val bedTimeMinText = if (selectedMin == 0) "" else "${selectedMin}분"

        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            text = { Text("잠든 시간이 $bedTimeHourText $bedTimeMinText 인가요?") },
            confirmButton = {
                TextButton(onClick = {
                    shouldSaveRemData = true
                }) {
                    Text("예")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmationDialog = false
                }) {
                    Text("아니오")
                }
            }
        )
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "잠든 시간을 설정해 주세요", color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))

                AndroidView(factory = { context ->
                    val view = LayoutInflater.from(context).inflate(R.layout.timepicker_layout, null, false)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )

                    view.layoutParams = layoutParams
                    val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
                    timePicker.hour = selectedHour
                    timePicker.minute = selectedMin

                    timePicker.setOnTimeChangedListener { _, hour, min ->
                        selectedHour = hour
                        selectedMin = min
                    }
                    view
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))


                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                {
                    TextButton(onClick =
                    {
                        showConfirmationDialog = true
                    }) {
                        Text("확인", color = Color.White)
                    }
                }

            }
        }
    }

    if (shouldSaveRemData) {
        LaunchedEffect(Unit) {
            val sleepingTime = calculateSleepTime(wakeUpTime, selectedHour, selectedMin)
            remDataViewModel.addRem(RemEntity(0, wakeUpTime, sleepingTime)).invokeOnCompletion {
                shouldSaveRemData = false
                showConfirmationDialog = false
                onConfirm(false)
            }
        }
    }
}