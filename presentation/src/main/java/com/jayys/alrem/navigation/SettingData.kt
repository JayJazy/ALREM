package com.jayys.alrem.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingData(
    var pageNumber: Int,

    var alarmName: String,

    var dayOfWeekList: MutableList<Boolean>,
    var hour: Int,
    var min: Int,

    val bellName: String,
    var ringtoneStringUri: String,
    var bellVolume: Int,

    var ttsVolume: Int,

    var repeatMinute: Int,

    var switchState: MutableList<Boolean>
) : Parcelable