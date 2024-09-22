package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.alarm.AddAlarmUseCase
import com.jayys.alrem.usecase.alarm.DeleteAlarmUseCase
import com.jayys.alrem.usecase.alarm.DeleteAllAlarmsUseCase
import com.jayys.alrem.usecase.alarm.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.alarm.GetMaxIdUseCase
import com.jayys.alrem.usecase.alarm.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmDataViewModel @Inject constructor(
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val getMaxIdUseCase: GetMaxIdUseCase,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val deleteAllAlarmsUseCase: DeleteAllAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel()
{
    init { getAllAlarms() }

    private var _alarmData = MutableStateFlow<List<AlarmEntity>>(emptyList())
    val alarmData = _alarmData.asStateFlow()

    private var _maxId = MutableStateFlow(0)
    val maxId = _maxId.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private fun handleException(e: Exception, errorMessage: String) {
        _error.value = errorMessage
        e.printStackTrace()
    }

    private fun getAllAlarms() = viewModelScope.launch(Dispatchers.IO) {
        try {
            getAllAlarmsUseCase.invoke().collect { alarms ->
                _alarmData.value = alarms
            }
        } catch (e: Exception) {
            handleException(e, "알람 목록을 불러오는 중 에러가 발생했습니다.")
        }
    }

    fun getMaxId() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _maxId.value = getMaxIdUseCase.invoke()
        } catch (e: Exception) {
            handleException(e, "새 알람을 만들기 전 에러가 발생했습니다.")
        }
    }

    fun addAlarm(alarm : AlarmEntity) = viewModelScope.launch(Dispatchers.IO) {
        try {
            addAlarmUseCase.invoke(alarm)
            getAllAlarms()
        } catch (e: Exception) {
            handleException(e, "알람을 생성하는 중 에러가 발생했습니다.")
        }
    }

    fun updateAlarm(alarm : AlarmEntity) = viewModelScope.launch(Dispatchers.IO) {
        try {
            updateAlarmUseCase.invoke(alarm)
            getAllAlarms()
        } catch (e: Exception) {
            handleException(e, "알람을 업데이트하는 중 에러가 발생했습니다.")
        }
    }

    fun deleteAlarm(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            deleteAlarmUseCase.invoke(id)
            getAllAlarms()
        } catch (e: Exception) {
            handleException(e, "알람을 삭제하는 중 에러가 발생했습니다.")
        }
    }


    fun deleteAllAlarms() = viewModelScope.launch(Dispatchers.IO) {
        try {
            deleteAllAlarmsUseCase.invoke()
            getAllAlarms() // 모든 알람 삭제 후 갱신
        } catch (e: Exception) {
            handleException(e, "모든 알람을 삭제하는 중 에러가 발생했습니다.")
        }
    }

}