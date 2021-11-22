package com.lwise.util

import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import se.michaelthelin.spotify.SpotifyApi
import java.net.URL

object SpotifyClient {

    private val dotenv = dotenv {
        ignoreIfMissing = true
    }
    private val CLIENT_ID = dotenv["SPOTIFY_CLIENT_ID"]
    private val CLIENT_SECRET = dotenv["SPOTIFY_CLIENT_SECRET"]
    private val spotifyApi = SpotifyApi.Builder()
        .setClientId(CLIENT_ID)
        .setClientSecret(CLIENT_SECRET)
        .build()
    private val clientCredentialsRequest = spotifyApi.clientCredentials().build()
    var spotifyTimeout: Long

    init {
        spotifyTimeout = requestAuthToken()
    }

    fun getTrackInfo(spotifyUrl: URL): String? {
        if (!spotifyUrl.toString().contains("track")) return null
        val trackId = spotifyUrl.getSpotifyResourceId()
        val trackRequest = spotifyApi.getTrack(trackId).build()
        val track = trackRequest.execute()
        return "${track.name} - ${track.artists.first().name}"
    }

    @Suppress("UNUSED_VARIABLE")
    fun launchSpotifyAuthRoutine(timeInterval: Long) {
        val job = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(timeInterval) {
            spotifyTimeout = requestAuthToken()
        }
    }

    private fun URL.getSpotifyResourceId(): String {
        return this.getLastPathSegment().split("?")[0]
    }

    private fun requestAuthToken(): Long {
        val clientCredentials = clientCredentialsRequest.execute()
        spotifyApi.accessToken = clientCredentials.accessToken
        return clientCredentials.expiresIn.toLong()
    }
}
