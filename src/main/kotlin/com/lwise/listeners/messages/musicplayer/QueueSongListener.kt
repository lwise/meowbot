package com.lwise.listeners.messages.musicplayer

import com.lwise.clients.SpotifyClient
import com.lwise.listeners.messages.MessageListener
import com.lwise.player.MusicPlayer
import com.lwise.types.events.MessageEvent
import com.lwise.util.asUrlOrNull
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class QueueSongListener : MessageListener {
    override val regexString: String
        get() = "m!queue\\s.*"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val messageContent = responseVector.message.content.split(" ").drop(1)
        val url = messageContent[0].asUrlOrNull()
        with(url?.toString() ?: "") {
            when {
                isNullOrBlank() -> MusicPlayer.addTrackToQueue("ytsearch:" + messageContent.joinToString(" "), responseVector.channel)
                contains("playlist") && contains("spotify") -> {
                    // We need to do this async because it takes forever for long playlists
                    SpotifyClient.getPlaylistItemsTrackInfo(url!!)?.forEach { playlistItemInfo ->
                        // I hate that we have to query for each song, but the playlist api doesn't return artists
                        MusicPlayer.addTrackToQueue("ytsearch:$playlistItemInfo", responseVector.channel)
                    }
                }
                contains("spotify") -> {
                    MusicPlayer.addTrackToQueue("ytsearch:" + SpotifyClient.getTrackInfo(url!!), responseVector.channel)
                }
                else -> MusicPlayer.addTrackToQueue(this, responseVector.channel)
            }
        }
        return Mono.empty()
    }
}
