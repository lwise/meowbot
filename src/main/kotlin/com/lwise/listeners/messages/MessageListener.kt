package com.lwise.listeners.messages

import com.lwise.listeners.Listener
import com.lwise.types.events.MessageEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono
import java.util.Locale

interface MessageListener : Listener<MessageEvent> {
    // use lowercase match
    val regexString: String

    override fun isTriggered(content: String): Boolean {
        val regex = regexString.toRegex()
        return regex.containsMatchIn(content.lowercase(Locale.getDefault()))
    }

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        getResponseMessage().takeIf { it.isNotEmpty() && it.isNotBlank() }?.let {
            return responseVector.channel.createMessage(it)
        }
        return Mono.empty()
    }

    fun getResponseMessage(): String
}
