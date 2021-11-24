package com.lwise.clients

import com.lwise.util.log
import com.lwise.util.logException
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object FortuneClient {
    private var client = OkHttpClient()
    private val url = HttpUrl.Builder()
        .scheme("https")
        .host("fortuneapi.herokuapp.com")
        .addPathSegment("fortunes")
        .build()

    fun getFortune(): String? {
        val queryFortuneUrl = url.toUrl()
        log(this::class.java.name, "Sending GET request to: $queryFortuneUrl")
        val request = Request.Builder().url(queryFortuneUrl).build()

        return try {
            val response = client.newCall(request).execute()
            val result = response.body!!
            log(this::class.java.name, result.toString())
            result.string()
        } catch (exception: IOException) {
            logException(this::class.java.name, "Cannot retrieve fortune from $queryFortuneUrl", exception)
            "I was going to read your fortune, but the future looks a little blurry..."
        }
    }
}
