package com.jayys.alrem

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jayys.alrem.destination.DestinationLayout
import com.jayys.alrem.destination.dismiss.DismissAlarm
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import com.jayys.alrem.usecase.datastore.SaveWakeUpTimeUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.Calendar


@RunWith(AndroidJUnit4::class)
class AlarmOffTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var dismissAlarmMock: DismissAlarm
    private lateinit var saveSwitchUseCaseMock: SaveSwitchUseCase
    private lateinit var saveWakeUpTimeUseCaseMock: SaveWakeUpTimeUseCase

    @Before
    fun setup() {
        // 초기화 및 mock 객체 설정
        dismissAlarmMock = mock(DismissAlarm::class.java)
        saveSwitchUseCaseMock = mock(SaveSwitchUseCase::class.java)
        saveWakeUpTimeUseCaseMock = mock(SaveWakeUpTimeUseCase::class.java)
    }

    @Test
    fun testIconButtonClickWithoutRem() {
        // Given
        val dayOfWeekMap = mapOf(
            "일" to Calendar.SUNDAY,
            "월" to Calendar.MONDAY,
            "화" to Calendar.TUESDAY,
            "수" to Calendar.WEDNESDAY,
            "목" to Calendar.THURSDAY,
            "금" to Calendar.FRIDAY,
            "토" to Calendar.SATURDAY
        )

        val selectedDates = dayOfWeekMap.values.map { dayOfWeek ->
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, dayOfWeek)
                set(Calendar.HOUR_OF_DAY, 18)
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
        }

        val testAlarm = AlarmEntity(
            id = 1,
            pageNum = 0,
            title = "Test Alarm",
            alarmDate = selectedDates,
            alarmDayOfWeek = mutableListOf(true, true, true, true, true, true, true),
            bellName = "",
            bellRingtone = "",
            bell = true,
            bellVolume = 1,

            vibration = false,

            tts = false,
            ttsVolume = 1,

            repeat = false,
            repeatMinute = 5,

            rem = false

        )

        composeTestRule.setContent {
            DestinationLayout(
                alarm = testAlarm,
                dismissAlarm = dismissAlarmMock,
                saveSwitchUseCase = saveSwitchUseCaseMock,
                saveWakeUpTimeUseCase = saveWakeUpTimeUseCaseMock
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("alarm_off").performClick()


        // Then
        verify(dismissAlarmMock).getCancelPendingIntent(eq(testAlarm.id), any())

    }
}