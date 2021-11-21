package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class QueueSongListener : MessageListener {
    override val regexString: String
        get() = "m!queue\\s.*"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val url = responseVector.message.content.split(" ")[1]
        MusicPlayer.addTrackToQueue(url)
        return Mono.empty()
    }
}
