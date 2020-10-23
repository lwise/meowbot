package com.lwise.listeners.messages

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

class MeowListener : MessageListener {
    override val trigger = "meow"
    override val response = "meow!"
    override fun respond(channel: MessageChannel) : Mono<Message>{
        return channel.createMessage(response)
    }
}