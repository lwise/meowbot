package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.asUrlOrNull
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class QueueSongListener : MessageListener {
    override val regexString: String
        get() = "m!queue\\s.*"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val messageContent = responseVector.message.content.split(" ").drop(1)
        val firstArg = messageContent[0].asUrlOrNull()
        val musicArg = firstArg?.toString() ?: ("ytsearch:" + messageContent.joinToString(" "))
        MusicPlayer.addTrackToQueue(musicArg)
        return Mono.empty()
    }
}
