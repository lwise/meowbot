package com.lwise.util

import com.lwise.listeners.messages.MessageListener
import com.lwise.listeners.reactions.ReactionListener
import com.lwise.types.toMessageEvent
import com.lwise.types.toReactionAddEvent
import com.lwise.types.toReactionRemoveEvent
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.event.domain.message.ReactionRemoveEvent
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
        .filter { event ->
            // don't respond to messages from other bots
            event.message.author.map { !it.isBot }.orElse(false)
        }
        .filter { event ->
            listener = listeners.firstOrNull { it.isTriggered(event.message.content) }
            listener != null
        }
        .map(MessageCreateEvent::toMessageEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}

fun GatewayDiscordClient.subscribeToReactionAdds(listeners: List<ReactionListener>) {
    var listener: ReactionListener? = null
    eventDispatcher.on(ReactionAddEvent::class.java)
        .filter { it.emoji.asCustomEmoji().isPresent }
        .filter { event ->
            event.emoji.asCustomEmoji().get().name.let { name ->
                listener = listeners.firstOrNull { it.isTriggered(name) }
            }
            listener != null
        }
        .map(ReactionAddEvent::toReactionAddEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}

fun GatewayDiscordClient.subscribeToReactionRemoves(listeners: List<ReactionListener>) {
    var listener: ReactionListener? = null
    eventDispatcher.on(ReactionRemoveEvent::class.java)
        .filter { it.emoji.asCustomEmoji().isPresent }
        .filter { event ->
            event.emoji.asCustomEmoji().get().name.let { name ->
                listener = listeners.firstOrNull { it.isTriggered(name) }
            }
            listener != null
        }
        .map(ReactionRemoveEvent::toReactionRemoveEvent)
        .flatMap {
            listener?.respond(it)
        }
        .subscribe()
}
