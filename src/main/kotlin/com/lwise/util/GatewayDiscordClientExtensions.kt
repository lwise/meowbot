package com.lwise.util

import com.lwise.listeners.messages.MessageListener
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlin.String

fun GatewayDiscordClient.subscribeToReady() {
    eventDispatcher.on(ReadyEvent::class.java).subscribe { event: ReadyEvent ->
        event.self.let {
            log(this.javaClass.name, String.format("Logged in as %s#%s", it.username, it.discriminator))
        }
    }
}

fun GatewayDiscordClient.subscribeToMessages(listeners: List<MessageListener>) {
    var listener: MessageListener? = null
    eventDispatcher.on(MessageCreateEvent::class.java)
        .map { it.message }
        .filter { message ->
            // don't respond to messages from other bots
            message.author.map { !it.isBot }.orElse(false)
        }
        .filter { message ->
            listener = listeners.firstOrNull { it.isTriggered(message.content) }
            listener != null
        }
        .flatMap(Message::getChannel)
        .flatMap { channel ->
            listener?.respond(channel)
        }
        .subscribe()
}
