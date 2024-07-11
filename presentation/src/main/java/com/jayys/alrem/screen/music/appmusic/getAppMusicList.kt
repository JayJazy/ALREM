package com.jayys.alrem.screen.music.appmusic

import android.content.Context
import android.net.Uri
import com.jayys.alrem.R
import com.jayys.alrem.screen.music.RingtoneData

fun getAppMusicList(context: Context): List<RingtoneData> = listOf(
    R.raw.dawn,
    R.raw.cheerful_morning,
    R.raw.leisurely_morning,
    R.raw.track1,
    R.raw.track2,
    R.raw.track3
).map { resId ->
    val title = context.resources.getResourceEntryName(resId)
    val contentUri = Uri.parse("android.resource://${context.packageName}/$resId")
    RingtoneData(title, contentUri)
}