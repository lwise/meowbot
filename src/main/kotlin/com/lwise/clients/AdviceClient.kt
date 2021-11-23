package com.lwise.clients

import com.lwise.util.log
import com.lwise.util.logException
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

        return try {
            val response = client.newCall(request).execute()
            val result = JSONObject(response.body!!.string())
            log(this::class.java.name, result.toString())
            result.getJSONObject("slip").get("advice").toString()
        } catch (exception: IOException) {
            logException(this::class.java.name, "Cannot retrieve advice from $queryAdviceUrl", exception)
            "I'd give you some advice, but I forgot all of it~!!"
        }
    }
}
