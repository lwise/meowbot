package com.lwise.util

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object CatFactClient {
    private var client = OkHttpClient()
    private val url = HttpUrl.Builder()
        .scheme("https")
        .host("cat-fact.herokuapp.com")
        .addPathSegment("facts")
        .addQueryParameter("amount", "1")
        .build()

    fun getCatFact(): String? {
        val queryFactUrl = url.toUrl()
        log(this::class.java.name, "Sending GET request to: $queryFactUrl")
        val request = Request.Builder().url(queryFactUrl).build()

        try {
            val response = client.newCall(request).execute()
            val result = JSONObject(response.body!!.string())
            log(this::class.java.name, result.toString())
            return result.getJSONArray("all").getJSONObject(0).get("text").toString()
        } catch (exception: IOException) {
            logException(this::class.java.name, "Cannot retrieve fact from $queryFactUrl", exception)
            return null
        }
    }
}