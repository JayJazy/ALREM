package com.jayys.alrem.component

import android.content.Context
import com.jayys.alrem.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun getRawResourceUri(context: Context): String {
    val resourceId = R.raw.dawn
    val uri = "android.resource://${context.packageName}/$resourceId"
    return URLEncoder.encode(uri, StandardCharsets.UTF_8.toString())
}