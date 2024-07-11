package com.jayys.alrem.screen.music.storagemusic

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.jayys.alrem.screen.music.RingtoneData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun getStorageMusicList(context: Context): List<RingtoneData> = withContext(Dispatchers.IO) {
    val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE
    )
    val selection = "${MediaStore.Audio.Media.MIME_TYPE} = ?"
    val selectionArgs = arrayOf("audio/mpeg")
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    context.contentResolver.query(
        collection,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)

        buildList {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                add(RingtoneData(title, contentUri))
            }
        }
    } ?: emptyList()
}