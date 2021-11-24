package com.lwise.listeners.messages

import com.lwise.player.MusicPlayer
import com.lwise.types.events.MessageEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class ClearQueueListener : MessageListener {
    override val regexString: String
        get() = "m!clear"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        MusicPlayer.clearQueue()
        return responseVector.channel.createMessage("Cleared! /ᐠ≧◡≦ᐟ\\\\")
    }
}
