package com.jayys.alrem.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun IgnoreBatteryDialog(onDismiss: () -> Unit, onConfirm: () -> Unit)
{
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))


                Text(text = "ALREM은 배터리 사용량을 많이 요구하지 않습니다.\n" +
                        "\n" +
                        "그러나 휴대폰에서 자체적으로 최적화 모드가 될 경우 설정된 알람이 무시되어 알람이 동작하지 않을 수 있습니다.\n" +
                        "\n" +
                        "알람이 울리지 않는다면 권한을 허용해 주세요", color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("취소", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick =
                    {
                        onConfirm()
                        onDismiss()
                    }) {
                        Text("확인", color = Color.White)
                    }
                }
            }
        }
    }
}