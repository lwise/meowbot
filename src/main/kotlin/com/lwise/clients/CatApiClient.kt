package com.lwise.clients

import com.lwise.util.ConfigUtil
import com.lwise.util.log
import com.lwise.util.logException
import io.github.cdimascio.dotenv.dotenv
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

object CatApiClient {

    private val dotenv = dotenv {
        ignoreIfMissing = true
    }

    private val API_KEY = dotenv["CAT_API_KEY"]

    private var client = OkHttpClient()

    private val base_url = HttpUrl.Builder()
        .scheme("https")
        .host("api.thecatapi.com")
        .addPathSegment("v1")
        .build()

    fun getRandomCatImageUrl(): String {
        val queryImageUrl = base_url.newBuilder()
            .addPathSegments("images/search")
            .addQueryParameter("limit", "1")
            .build().toUrl()

        log(this::class.java.name, "Sending GET request to: $queryImageUrl")

        val request = Request.Builder()
            .url(queryImageUrl)
            .header("x-api-key", API_KEY)
            .build()

        return try {
            val response = client.newCall(request).execute()
            val result = JSONArray(response.body!!.string())
            log(this::class.java.name, result.toString())

            val imageUrl = result.getJSONObject(0).get("url").toString()

            imageUrl
        } catch (e: IOException) {
            logException(this::class.java.name, "Cannot retrieve image from: $queryImageUrl", e)

            "Cannot retrieve cat pic ${ConfigUtil.emoji["crying"]}"
        }
    }
}
