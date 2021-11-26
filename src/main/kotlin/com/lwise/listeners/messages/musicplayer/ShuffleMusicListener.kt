package com.lwise.listeners.messages.musicplayer

import com.lwise.listeners.messages.MessageListener
import com.lwise.player.MusicPlayer
import com.lwise.types.events.MessageEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class ShuffleMusicListener : MessageListener {
    override val regexString: String
        get() = "m!shuffle"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        MusicPlayer.shuffle()
        return super.respond(responseVector)
    }
}
