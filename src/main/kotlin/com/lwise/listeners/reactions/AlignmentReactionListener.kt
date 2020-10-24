package com.lwise.listeners.reactions

import com.lwise.alignment.AlignmentDefinitions.Companion.EMOJI_NAME_LIST
import com.lwise.types.ReactionEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class AlignmentReactionListener : ReactionListener {
    override fun isTriggered(content: String): Boolean {
        return EMOJI_NAME_LIST.contains(content)
    }

    override fun respond(responseVector: ReactionEvent): Mono<Message> {
        // TODO: Add functionality once we have a database set up
        return Mono.empty()
    }
}
