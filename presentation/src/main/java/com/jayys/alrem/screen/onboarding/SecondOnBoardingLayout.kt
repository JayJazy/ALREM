package com.jayys.alrem.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R


@Composable
fun SecondOnBoardingLayout()
{
    Column(
        modifier = Modifier.fillMaxSize().semantics { contentDescription = "두 번째 온보딩 화면" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.chart),
            contentDescription = stringResource(id = R.string.description_second_onboarding_image),
            modifier = Modifier.size(300.dp))

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
        ) {
            Text(
                text = "수면 시간을 기록 할 수 있어요",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "나의 평균 수면 시간을 확인하고 수면 기록을 바탕으로 수면 주기를 개선하세요",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview
@Composable
fun PreviewSecondOnBoardingLayout()
{
    SecondOnBoardingLayout()
}