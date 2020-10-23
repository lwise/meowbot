package com.lwise.listeners

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

interface Listener {

    fun respond(channel: MessageChannel): Mono<Message>
    fun isTriggered(content: String): Boolean
}
