package com.lwise.listeners.messages

import com.lwise.listeners.Listener
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

interface MessageListener : Listener {
    // use lowercase match
    val regexString: String

    override fun isTriggered(content: String): Boolean {
        val regex = regexString.toRegex()
        return regex.containsMatchIn(content.toLowerCase())
    }

    override fun respond(channel: MessageChannel): Mono<Message> {
        return channel.createMessage(getResponseMessage())
    }

    fun getResponseMessage(): String
}
