package com.lwise.listeners.reactions

import com.lwise.listeners.Listener
import com.lwise.types.events.ReactionEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

interface ReactionListener : Listener<ReactionEvent> {
    override fun isTriggered(content: String): Boolean

    override fun respond(responseVector: ReactionEvent): Mono<Message>
}
