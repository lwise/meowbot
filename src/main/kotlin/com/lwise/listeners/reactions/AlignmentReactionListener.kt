package com.lwise.listeners.reactions

import com.lwise.alignment.AlignmentDefinitions.Companion.EMOJI_NAMES
import com.lwise.clients.DatabaseClient
import com.lwise.transformers.UserDataTransformer
import com.lwise.types.UserData
import com.lwise.types.events.ReactionEvent
import com.lwise.types.events.ReactionEventType
import com.lwise.util.ConfigUtil
import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono
import java.lang.Exception
import kotlin.math.max

class AlignmentReactionListener : ReactionListener {
    override fun isTriggered(content: String): Boolean {
        return EMOJI_NAMES.contains(content)
    }

    override fun respond(responseVector: ReactionEvent): Mono<Message> {
        if (responseVector.guild.roles.collectList().block()?.map { role -> role.id }?.contains(Snowflake.of(ConfigUtil.alignmentRoles["True Neutral"]!!)) == true) {
            val discordUserToUpdate = responseVector.userReactedTo ?: return Mono.empty()
            val userFetchQuery = "SELECT * FROM users WHERE username = '${discordUserToUpdate.username}';"

            // prevent people from voting themselves
            if (discordUserToUpdate == responseVector.reactingUser) {
                return Mono.empty()
            }

            // else continue and update database
            val userFromDatabase = DatabaseClient.query(userFetchQuery, UserDataTransformer())

            userFromDatabase?.let { user ->
                val triggerEmojiName = responseVector.emoji.asCustomEmoji().takeIf { it.isPresent }?.get()?.name
                triggerEmojiName?.let {
                    val newPoints = calculateNewPoints(user, triggerEmojiName, responseVector.eventType)
                    val userUpdateQuery =
                        "UPDATE users SET ${triggerEmojiName}_points = $newPoints WHERE id = '${user.id}';"
                    DatabaseClient.update(userUpdateQuery)
                }
            }
        }
        return Mono.empty()
    }

    private fun calculateNewPoints(user: UserData, emojiName: String, reactionEventType: ReactionEventType): Int {
        val basePoints = when (emojiName) {
            "chaotic" -> user.chaoticPoints
            "lawful" -> user.lawfulPoints
            "good" -> user.goodPoints
            "evil" -> user.evilPoints
            else -> throw Exception("We should never be calculating points on other emojis")
        }
        return if (reactionEventType == ReactionEventType.ADDED) basePoints + 1 else max(basePoints - 1, 0)
    }
}
