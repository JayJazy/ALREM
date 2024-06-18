package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.AddAlarmUseCase
import com.jayys.alrem.usecase.DeleteAlarmUseCase
import com.jayys.alrem.usecase.DeleteAllAlarmsUesCase
import com.jayys.alrem.usecase.GetAllAlarmsUseCase
import com.jayys.alrem.usecase.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmDataViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val deleteAllAlarmsUesCase: DeleteAllAlarmsUesCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel()
{
    init { getAllAlarms() }

    private var _alarmData = MutableStateFlow<List<AlarmEntity>>(emptyList())
    val alarmData = _alarmData.asStateFlow()


    fun addAlarm(alarm : AlarmEntity) = viewModelScope.launch(Dispatchers.IO) {
        addAlarmUseCase.invoke(alarm)
    }

    fun updateAlarm(alarm : AlarmEntity) = viewModelScope.launch(Dispatchers.IO) {
        updateAlarmUseCase.invoke(alarm)
    }

    fun deleteAlarm(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteAlarmUseCase.invoke(id)
    }



    fun deleteAllAlarms() = viewModelScope.launch(Dispatchers.IO) {
        deleteAllAlarmsUesCase.invoke()
    }



    fun getAllAlarms() = viewModelScope.launch {
        getAllAlarmsUseCase.invoke().collect { alarms ->
            _alarmData.value = alarms
        }
    }
}