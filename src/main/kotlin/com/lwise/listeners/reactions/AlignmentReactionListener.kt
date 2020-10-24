package com.lwise.listeners.reactions

import com.lwise.alignment.AlignmentDefinitions.Companion.EMOJI_NAMES
import com.lwise.types.ReactionEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class AlignmentReactionListener : ReactionListener {
    override fun isTriggered(content: String): Boolean {
        return EMOJI_NAMES.contains(content)
    }

    override fun respond(responseVector: ReactionEvent): Mono<Message> {
        // TODO: Add functionality once we have a database set up
        return Mono.empty()
    }
}
