package com.lwise.util

import java.lang.Exception
import java.net.URL

fun String.asUrlOrNull(): URL? {
    return try {
        URL(this)
    } catch (e: Exception) {
        null
    }
}

fun URL.getLastPathSegment(): String {
    val urlString = toString()
    return urlString.substring(urlString.lastIndexOf('/') + 1)
}
