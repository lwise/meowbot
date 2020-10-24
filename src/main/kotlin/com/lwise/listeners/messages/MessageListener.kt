package com.lwise.listeners.messages

import com.lwise.listeners.Listener
import com.lwise.types.MessageEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

interface MessageListener : Listener<MessageEvent> {
    // use lowercase match
    val regexString: String

    override fun isTriggered(content: String): Boolean {
        val regex = regexString.toRegex()
        return regex.containsMatchIn(content.toLowerCase())
    }

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        return responseVector.channel.createMessage(getResponseMessage())
    }

    fun getResponseMessage(): String
}
