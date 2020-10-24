package com.lwise.listeners.messages

import com.lwise.listeners.Listener
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import reactor.core.publisher.Mono

interface MessageListener : Listener<MessageChannel> {
    // use lowercase match
    val regexString: String

    override fun isTriggered(content: String): Boolean {
        val regex = regexString.toRegex()
        return regex.containsMatchIn(content.toLowerCase())
    }

    override fun respond(responseVector: MessageChannel): Mono<Message> {
        return responseVector.createMessage(getResponseMessage())
    }

    fun getResponseMessage(): String
}
