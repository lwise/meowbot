package com.lwise.types

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.event.domain.message.ReactionRemoveEvent

enum class ReactionEventType {
    ADDED,
    REMOVED
}

data class ReactionEvent(
    val channel: MessageChannel,
    val emoji: ReactionEmoji,
    val message: Message,
    val reactingUser: User,
    val userReactedTo: User,
    val eventType: ReactionEventType
)

fun ReactionAddEvent.toReactionAddEvent(): ReactionEvent {
    val messageData = message.block()!!
    return ReactionEvent(
        channel = channel.block()!!,
        emoji = emoji,
        message = messageData,
        reactingUser = user.block()!!,
        userReactedTo = messageData.author.get(),
        eventType = ReactionEventType.ADDED
    )
}

fun ReactionRemoveEvent.toReactionRemoveEvent(): ReactionEvent {
    val messageData = message.block()!!
    return ReactionEvent(
        channel = channel.block()!!,
        emoji = emoji,
        message = messageData,
        reactingUser = user.block()!!,
        userReactedTo = messageData.author.get(),
        eventType = ReactionEventType.REMOVED
    )
}
