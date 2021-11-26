package com.lwise.types.events

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import discord4j.core.event.domain.message.MessageCreateEvent
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
    val userReactedTo: User?,
    val eventType: ReactionEventType,
    val guildId: Snowflake
)

data class MessageEvent(
    val message: Message,
    val guild: Guild,
    val channel: MessageChannel,
    val author: Member?
)

fun ReactionAddEvent.toReactionAddEvent(): ReactionEvent {
    val messageData = message.block()!!
    return ReactionEvent(
        channel = channel.block()!!,
        emoji = emoji,
        message = messageData,
        reactingUser = user.block()!!,
        userReactedTo = messageData.author.takeIf { it.isPresent }?.get(),
        eventType = ReactionEventType.ADDED,
        guildId = guildId.get()
    )
}

fun ReactionRemoveEvent.toReactionRemoveEvent(): ReactionEvent {
    val messageData = message.block()!!
    return ReactionEvent(
        channel = channel.block()!!,
        emoji = emoji,
        message = messageData,
        reactingUser = user.block()!!,
        userReactedTo = messageData.author.takeIf { it.isPresent }?.get(),
        eventType = ReactionEventType.REMOVED,
        guildId = guildId.get()
    )
}

fun MessageCreateEvent.toMessageEvent(): MessageEvent {
    return MessageEvent(
        message = message,
        guild = guild.block()!!,
        channel = message.channel.block()!!,
        author = member.takeIf { it.isPresent }?.get()
    )
}
