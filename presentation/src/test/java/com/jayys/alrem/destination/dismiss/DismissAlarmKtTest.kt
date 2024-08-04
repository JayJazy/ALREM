package com.jayys.alrem.destination.dismiss

import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar
import java.util.Date


class DismissAlarmKtTest{

    private fun findClosestDate(now: Calendar, selectedDays: List<String>, alarmDates: List<Date>): Date? {
        val dayMap = mapOf(
            "일" to Calendar.SUNDAY,
            "월" to Calendar.MONDAY,
            "화" to Calendar.TUESDAY,
            "수" to Calendar.WEDNESDAY,
            "목" to Calendar.THURSDAY,
            "금" to Calendar.FRIDAY,
            "토" to Calendar.SATURDAY)

        val candidateDates = mutableListOf<Date>()
        val firstAlarmDate = alarmDates.first()
        val calendar = Calendar.getInstance().apply {
            time = firstAlarmDate
        }

        val alarmHour = calendar.get(Calendar.HOUR_OF_DAY)
        val alarmMin = calendar.get(Calendar.MINUTE)

        for(day in selectedDays)
        {
            val targetDay = dayMap[day] ?: continue
            val closestAlarmDate = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, targetDay)
                set(Calendar.HOUR_OF_DAY, alarmHour)
                set(Calendar.MINUTE, alarmMin)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (before(now)) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }.time

            candidateDates.add(closestAlarmDate)
        }

        return candidateDates.minByOrNull { it.time - now.timeInMillis }
    }

    // 현재 시간 설정 (예: 2024년 8월 4일 일요일 10:00)
    private val now = Calendar.getInstance().apply {
        set(2024, Calendar.AUGUST, 4, 20, 15, 0)
        set(Calendar.MILLISECOND, 0)
    }

    // 알람 시간 설정 - 오후 8시 10분
    private val alarmDate = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 20)
        set(Calendar.MINUTE, 10)
        set(Calendar.MILLISECOND, 0)
    }.time



    @Test // 테스트 케이스 1: 가장 가까운 날짜가 이번 주인 경우
    fun testFindClosestDate1() {
        run {
            val selectedDays = listOf("목", "토")
            val result = findClosestDate(now, selectedDays, listOf(alarmDate))
            val expected = Calendar.getInstance().apply {
                set(2024, Calendar.AUGUST, 10, 20, 10, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            assertEquals(expected, result)
        }
    }


    @Test // 테스트 케이스 2: 가장 가까운 날짜가 현재 요일인 경우
    fun testFindClosestDate2()
    {
        run {
            val selectedDays = listOf("월", "수", "금")
            val result = findClosestDate(now, selectedDays, listOf(alarmDate))
            val expected = Calendar.getInstance().apply {
                set(2024, Calendar.AUGUST, 12, 20, 10, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            assertEquals(expected, result)
        }
    }

    @Test // 테스트 케이스 3: 요일이 하루만 지정된 경우
    fun testFindClosestDate3()
    {
        run {
            val selectedDays = listOf("일")
            val result = findClosestDate(now, selectedDays, listOf(alarmDate))
            val expected = Calendar.getInstance().apply {
                set(2024, Calendar.AUGUST, 11, 20, 10, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            assertEquals(expected, result)
        }

    }


    @Test  // 테스트 케이스 4: 모든 요일이 선택된 경우
    fun testFindClosestDate4()
    {
        run {
            val selectedDays = listOf("일", "월", "화", "수", "목", "금", "토")
            val result = findClosestDate(now, selectedDays, listOf(alarmDate))
            val expected = Calendar.getInstance().apply {
                set(2024, Calendar.AUGUST, 10, 20, 10, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            assertEquals(expected, result)
        }
    }

}