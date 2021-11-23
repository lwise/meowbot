package com.lwise.listeners.messages.musicplayer

import com.lwise.listeners.messages.MessageListener
import com.lwise.types.events.MessageEvent
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class SkipSongListener : MessageListener {
    override val regexString: String
        get() = "m!skip"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        MusicPlayer.skip()
        return super.respond(responseVector)
    }
}
