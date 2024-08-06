package com.jayys.alrem.screen.main


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R

@Composable
fun TopLayout(
    screenHeight: Dp,
    onNavigateToPreferencesScreen: () -> Unit
)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.15f))
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 35.dp, start = 25.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .weight(0.35f)
                .fillMaxWidth()) {
                Text("ALREM", fontSize = 33.sp, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }


            Box(modifier = Modifier
                .weight(0.35f)
                .fillMaxSize())

            Box(modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth()){
                Button(
                    onClick =
                    {
                        onNavigateToPreferencesScreen()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF939393)),
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.setting),
                        contentDescription = stringResource(id = R.string.description_inquiry_icon)
                    )
                }
            }
        }
    }
}