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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jayys.alrem.R
import com.jayys.alrem.dialog.PermissionDialog


@Composable
fun PreferencesScreen(onNavigateBackToMainScreen : () -> Unit)
{
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

        BoxWithConstraints {
            val screenHeight = maxHeight
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
                                arrayOf("android299@naver.com")
                            )  // 받는 사람 이메일 주소
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


