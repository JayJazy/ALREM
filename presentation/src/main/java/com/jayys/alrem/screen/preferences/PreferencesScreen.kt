package com.jayys.alrem.screen.preferences

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.dialog.IgnoreBatteryDialog
import com.jayys.alrem.dialog.PermissionDialog
import com.jayys.alrem.permission.PermissionManager


@Composable
fun PreferencesScreen(
    permissionManager: PermissionManager,
    onNavigateBackToMainScreen : () -> Unit
)
{
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }

    var isVisible by remember { mutableStateOf(permissionManager.checkIgnoreBatteryOptimizations()) }
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog)
    {
        IgnoreBatteryDialog(onDismiss = { showDialog = false }, onConfirm = {
            if (permissionManager.checkAndRequestIgnoreBatteryOptimizations()) {
                isVisible = true
            }
            showDialog = false
        })
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

        BoxWithConstraints {
            val screenHeight = maxHeight
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = statusBarHeight)
                    .height(screenHeight * 0.08f)
                    .background(MaterialTheme.colorScheme.onBackground), contentAlignment = Alignment.Center)
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp), contentAlignment = Alignment.CenterStart){
                            IconButton(onClick = { onNavigateBackToMainScreen() }) {
                                Icon(painter = painterResource(id = R.drawable.back_icon), contentDescription = "뒤로 가기", tint = Color.Gray)
                            }
                        }

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                            Text(text = "설정", color = Color.White)
                        }

                        Box(modifier = Modifier.weight(1f))
                    }
                }

                if(!isVisible){
                    Row(modifier = Modifier
                        .fillMaxWidth().height(85.dp)
                        .padding(vertical = 15.dp).padding(horizontal = 30.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFD5D5).copy(0.8f))
                        .clickable { if(!permissionManager.checkIgnoreBatteryOptimizations()) { showDialog = true } }, verticalAlignment = Alignment.CenterVertically)
                    {
                        Icon(painter = painterResource(id = R.drawable.error_icon),
                            contentDescription = "문제",
                            tint = Color.Red,
                            modifier = Modifier.padding(start = 20.dp, end = 10.dp))
                        Text(text = "알람이 울리지 않아요", color = Color.Red, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }




            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.08f)
                    .clickable { showPermissionDialog = true },
                    contentAlignment = Alignment.Center){
                    Text(text = "권한 설정", color = Color.White)
                }

                Spacer(modifier = Modifier.height(screenHeight * 0.02f))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.08f)
                    .clickable
                    {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(
                                Intent.EXTRA_EMAIL,
                                arrayOf("jayys2407@gmail.com")
                            )
                        }
                        val chooser = Intent.createChooser(emailIntent, "이메일 클라이언트를 선택하세요")
                        if (emailIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(chooser)
                        } else {
                            Toast
                                .makeText(context, "이메일 클라이언트 앱을 설치해야 합니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    contentAlignment = Alignment.Center){
                    Text(text = "문의 사항", color = Color.White)
                }
            }
        }
    }

    
    if(showPermissionDialog){
        PermissionDialog(onDismiss = { showPermissionDialog = false })
    }
}


