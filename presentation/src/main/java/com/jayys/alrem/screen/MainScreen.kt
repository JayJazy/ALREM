package com.jayys.alrem.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.component.AddButton

@Composable
fun MainScreen(
    onNavigateToAlarmAddScreen : () -> Unit
)
{
    val onNavigateToAlarmAddScreen = onNavigateToAlarmAddScreen

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background))
    {
        BoxWithConstraints {
            val screenHeight = maxHeight
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopLayout(screenHeight)
                TopItemLayout(screenHeight)
                AlarmListLayout(screenHeight)
                AlarmAddOnMainLayout(screenHeight, onNavigateToAlarmAddScreen)
                Box(contentAlignment = Alignment.BottomCenter)
                {
                    AdvertisementLayout1(screenHeight)
                }

            }
        }

    }
}


@Composable
fun TopLayout(screenHeight: Dp)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.15f))
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, start = 30.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box{
                Text("ALREM", fontSize = 40.sp, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
            
            Box{
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF939393)),
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.setting),
                        contentDescription = "inquiry"
                    )
                }
            }
        }
    }
}


@Composable
fun TopItemLayout(screenHeight: Dp)
{
    val (selectedBox, setSelectedBox) = remember { mutableStateOf("알 람") }

    val getBoxBackground = { boxName: String ->
        if (boxName == selectedBox)
            Brush.linearGradient(colors = listOf(Color(0xFFB98FFE), Color(0xFF8FC9FE)))
        else
            Brush.linearGradient(colors = listOf(Color(0xFF393C41), Color(0xFF393C41)))
    }

    val boxData = listOf(
        Pair("알 람", painterResource(id = R.drawable.alarm_icon)),
        Pair("수 면", painterResource(id = R.drawable.rem_icon))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.1f)
            .background(Color(0xFF212327)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(red = 0.22f, green = 0.24f, blue = 0.25f)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            boxData.forEach { (boxName, icon) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .background(getBoxBackground(boxName))
                        .clickable { setSelectedBox(boxName) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = boxName,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AlarmListLayout(screenHeight: Dp)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.58f))
    {

    }

}


@Composable
fun AlarmAddOnMainLayout(screenHeight: Dp, onNavigateToAlarmAddScreen: () -> Unit)
{
    AddButton(modifier = Modifier
        .clickable{ onNavigateToAlarmAddScreen() },
        text = "알 람 추 가", screenHeight = screenHeight)
}


@Composable
fun AdvertisementLayout1(screenHeight: Dp)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight * 0.09f)
        .padding(top = 10.dp)
        .background(Color.LightGray))
    {
        Text(text = "광 고", color = Color.White)
    }
}