package com.jayys.alrem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun AskForBackDialog(onDismiss: () -> Unit, onConfirm: () -> Unit)
{
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.height(180.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))


                Text(text = "변경 사항을 저장하지 않고 나갈까요?", color = Color.White)

                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier
                        .height(45.dp)
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFF72CCFF), shape = RoundedCornerShape(10.dp))
                        .clickable { onDismiss() },
                        contentAlignment = Alignment.Center)
                    {
                        Text(text = "취소", color = Color.White, style = MaterialTheme.typography.bodySmall, fontSize = 16.sp)
                    }

                    Box(modifier = Modifier
                        .height(45.dp)
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFFFF4545), shape = RoundedCornerShape(10.dp))
                        .clickable { onConfirm() },
                        contentAlignment = Alignment.Center)
                    {
                        Text(text = "나가기", color = Color.White, style = MaterialTheme.typography.bodySmall, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AskForBackDialogPreview() {
    val showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AskForBackDialog(
            onDismiss = {  },
            onConfirm = {  }
        )
    }
}