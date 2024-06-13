package com.jayys.alrem.screen.music.storagemusic

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.jayys.alrem.screen.music.RingtoneData


fun  getStorageMusicList(context : Context) : MutableList<RingtoneData>{
    val ringtoneList = mutableListOf<RingtoneData>()

    val collection: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE
    )

    val selection = "${MediaStore.Audio.Media.MIME_TYPE} = ?"
    val selectionArgs = arrayOf("audio/mpeg")

    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"
    val contentResolver: ContentResolver = context.contentResolver


    val cursor: Cursor? = contentResolver.query(
        collection,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)


        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val title = it.getString(titleColumn)

            val contentUri = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id.toString()
            )

            val ringtoneData = RingtoneData(title, contentUri)
            ringtoneList.add(ringtoneData)
        }
    }

    return ringtoneList
}