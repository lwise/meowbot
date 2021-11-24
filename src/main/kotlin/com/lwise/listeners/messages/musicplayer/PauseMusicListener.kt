package com.lwise.listeners.messages.musicplayer

import com.lwise.listeners.messages.MessageListener
import com.lwise.player.MusicPlayer
import com.lwise.types.events.MessageEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class PauseMusicListener : MessageListener {
    override val regexString: String
        get() = "m!pause"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        MusicPlayer.pause()
        return super.respond(responseVector)
    }
}
