package com.jayys.alrem.screen.music.appmusic

import android.content.Context
import android.net.Uri
import com.jayys.alrem.R
import com.jayys.alrem.screen.music.RingtoneData

fun getAppMusicList(context: Context) : MutableList<RingtoneData>
{
    val ringtoneList = mutableListOf<RingtoneData>()

    val rawResourceIds = listOf(
        R.raw.dawn
    )

    for (resId in rawResourceIds) {
        val title = context.resources.getResourceEntryName(resId)
        val contentUri = Uri.parse("android.resource://${context.packageName}/$resId")
        ringtoneList.add(RingtoneData(title, contentUri))
    }

    return ringtoneList
}