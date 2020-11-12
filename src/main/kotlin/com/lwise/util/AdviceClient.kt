package com.lwise.util

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object AdviceClient {
    private var client = OkHttpClient()
    private val url = HttpUrl.Builder()
        .scheme("https")
        .host("api.adviceslip.com")
        .addPathSegment("advice")
        .build()

    fun getAdvice(): String? {
        val queryAdviceUrl = url.toUrl()
        log(this::class.java.name, "Sending GET request to: $queryAdviceUrl")
        val request = Request.Builder().url(queryAdviceUrl).build()

        try {
            val response = client.newCall(request).execute()
            val result = JSONObject(response.body!!.string())
            log(this::class.java.name, result.toString())
            return result.getJSONObject("slip").get("advice").toString()
        } catch (exception: IOException) {
            logException(this::class.java.name, "Cannot retrieve advice from $queryAdviceUrl", exception)
            return null
        }
    }
}
