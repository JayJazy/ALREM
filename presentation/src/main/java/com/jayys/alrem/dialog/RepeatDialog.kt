package com.jayys.alrem.dialog

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jayys.alrem.databinding.NumberpickerBinding


@Composable
fun RepeatDialog(
    initialValue: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedMinute by remember { mutableIntStateOf(initialValue) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "반복 시간(분)을 설정 하세요", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                AndroidView(
                    factory = { context ->
                        val binding = NumberpickerBinding.inflate(LayoutInflater.from(context), null, false)

                        binding.numberPicker.apply {
                            minValue = 1
                            maxValue = 59
                            value = initialValue
                            setOnValueChangedListener { _, _, minute ->
                                selectedMinute = minute
                            }
                        }
                        binding.root
                    },
                    modifier = Modifier.wrapContentHeight()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("취소", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirm(selectedMinute) }) {
                        Text("확인", color = Color.White)
                    }
                }
            }
        }
    }
}