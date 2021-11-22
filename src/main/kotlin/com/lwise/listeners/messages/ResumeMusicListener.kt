package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class ResumeMusicListener : MessageListener {
    override val regexString: String
        get() = "m!play"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        MusicPlayer.resume()
        return super.respond(responseVector)
    }
}
