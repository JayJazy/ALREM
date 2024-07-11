package com.jayys.alrem.viemodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jayys.alrem.utils.getRawResourceUri
import com.jayys.alrem.navigation.SettingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingDataViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val _ringtoneName = MutableStateFlow("dawn")
    val ringtoneName = _ringtoneName.asStateFlow()
    fun bellNameToRingtoneNameAsStateFlow()
    {
        _ringtoneName.value = bellName
    }

    private val _selectedUri = MutableStateFlow<Uri?>(null)
    val selectedUri = _selectedUri.asStateFlow()
    fun setSelectedUri(uri: Uri) {
        _selectedUri.value = uri
    }


    var originalBellName = "dawn"
    var isUpdate = false


    var pageNumber = 0
    var alarmName by mutableStateOf("")
    var dayOfWeekList = mutableListOf(false, false, false, false, false, false, false)
    var hour = 7
    var min = 30
    var bellName = "dawn"
    var ringtoneStringUri = getRawResourceUri(context)
    var bellVolume = 2
    var ttsVolume = 2
    var repeatMinute = 5
    var switchState = mutableListOf(true, true, false, false, false)


    fun createSettingData(): SettingData {
        return SettingData(
            isUpdate = isUpdate,
            pageNumber = pageNumber,
            alarmName = alarmName,
            dayOfWeekList = dayOfWeekList,
            hour = hour,
            min = min,
            bellName = bellName,
            ringtoneStringUri = ringtoneStringUri,
            bellVolume = bellVolume,
            ttsVolume = ttsVolume,
            repeatMinute = repeatMinute,
            switchState = switchState
        )
    }

    fun separateSettingData(settingData: SettingData)
    {
        isUpdate = settingData.isUpdate
        pageNumber = settingData.pageNumber
        alarmName = settingData.alarmName
        dayOfWeekList = settingData.dayOfWeekList.toMutableList()
        hour = settingData.hour
        min = settingData.min
        bellName = settingData.bellName
        ringtoneStringUri = settingData.ringtoneStringUri
        bellVolume = settingData.bellVolume
        ttsVolume = settingData.ttsVolume
        repeatMinute = settingData.repeatMinute
        switchState = settingData.switchState.toMutableList()
    }
}

