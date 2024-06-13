package com.jayys.alrem.screen.music.systemmusic

import android.content.Context
import android.media.RingtoneManager
import com.jayys.alrem.screen.music.RingtoneData


fun getSystemMusicList(context: Context) : MutableList<RingtoneData>
{
    val ringtoneManager = RingtoneManager(context)
    ringtoneManager.setType(RingtoneManager.TYPE_RINGTONE)
    val cursor = ringtoneManager.cursor

    val ringtoneList = mutableListOf<RingtoneData>()
    while (cursor.moveToNext()) {
        val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        val uri = ringtoneManager.getRingtoneUri(cursor.position)
        ringtoneList.add(RingtoneData(title, uri))
    }
    cursor.close()
    return ringtoneList
}