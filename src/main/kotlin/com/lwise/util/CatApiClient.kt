package com.lwise.util

import io.github.cdimascio.dotenv.dotenv
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

object CatApiClient {

    val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val API_KEY = dotenv["DATABASE_URL"]

    var client = OkHttpClient()

    val base_url = HttpUrl.Builder()
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

        try {
            val response = client.newCall(request).execute()
            val result = JSONArray(response.body!!.string())
            log(this::class.java.name, result.toString())

            val imageUrl = result.getJSONObject(0).get("url").toString()

            return imageUrl
        } catch (e: IOException) {
            logException(this::class.java.name, "Cannot retrieve image from: $queryImageUrl", e)

            return "Cannot retrieve cat pic <:cri:698738886293323786>"
        }
    }
}
