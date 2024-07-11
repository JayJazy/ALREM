package com.jayys.alrem.screen.music.systemmusic

import android.content.Context
import android.media.RingtoneManager
import com.jayys.alrem.screen.music.RingtoneData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun getSystemMusicList(context: Context): List<RingtoneData> = withContext(Dispatchers.IO) {
    val ringtoneManager = RingtoneManager(context)
    ringtoneManager.setType(RingtoneManager.TYPE_RINGTONE)
    val cursor = ringtoneManager.cursor

    cursor.use { c ->
        buildList {
            while (c.moveToNext()) {
                val title = c.getString(RingtoneManager.TITLE_COLUMN_INDEX)
                val uri = ringtoneManager.getRingtoneUri(c.position)
                add(RingtoneData(title, uri))
            }
        }
    }
}