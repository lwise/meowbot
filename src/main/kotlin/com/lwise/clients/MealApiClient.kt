package com.lwise.clients

import com.lwise.util.log
import com.lwise.util.logException
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object MealApiClient {
    private var client = OkHttpClient()
    private val url = HttpUrl.Builder()
        .scheme("https")
        .host("www.themealdb.com")
        .addPathSegment("api")
        .addPathSegment("json")
        .addPathSegment("v1")
        .addPathSegment("1")
        .build()

    fun getRandomMeal(): String? {
        val queryUrl = url.newBuilder()
            .addPathSegments("random.php")
            .build().toUrl()
        log(this::class.java.name, "Sending GET request to: $queryUrl")
        val request = Request.Builder().url(queryUrl).build()

        return try {
            val response = client.newCall(request).execute()
            val result = JSONObject(response.body!!.string())
            log(this::class.java.name, result.toString())
            result.getJSONArray("meals").getJSONObject(0).get("strMeal").toString()
        } catch (exception: IOException) {
            logException(this::class.java.name, "Cannot retrieve advice from $queryUrl", exception)
            "I'd give you some advice, but I forgot all of it~!!"
        }
    }
}
