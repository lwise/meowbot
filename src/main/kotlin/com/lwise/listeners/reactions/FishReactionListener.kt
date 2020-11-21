package com.lwise.listeners.reactions

import com.lwise.types.events.ReactionEvent
import com.lwise.util.DatabaseClient
import com.lwise.util.UserDataTransformer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class FishReactionListener : ReactionListener {
    override fun isTriggered(content: String): Boolean {
        return content == "\uD83D\uDC1F" // fish emoji unicode
    }

    override fun respond(responseVector: ReactionEvent): Mono<Message> {
        if (wasAMeowbotFeedingRequest(responseVector)) {
            val userToGiveFishPointsTo = responseVector.reactingUser
            val userFetchQuery = "SELECT * FROM users WHERE username = '${userToGiveFishPointsTo.username}';"
            val userFromDatabase = DatabaseClient.query(userFetchQuery, UserDataTransformer())
            userFromDatabase?.let { user ->
                val newPoints = user.fishPoints + 1
                val userUpdateQuery = "UPDATE users SET fish_points = $newPoints WHERE id = '${userToGiveFishPointsTo.id.asBigInteger()}';"
                DatabaseClient.update(userUpdateQuery)
            } ?: run {
                // user isn't in the database yet
                val userQuery = "INSERT INTO users (id, username, fish_points) VALUES (${userToGiveFishPointsTo.id.asBigInteger()}, '${userToGiveFishPointsTo.username}', 1) ON CONFLICT DO NOTHING;"
                DatabaseClient.update(userQuery)
            }
        }
        return Mono.empty()
    }

    private fun wasAMeowbotFeedingRequest(responseVector: ReactionEvent): Boolean {
        return responseVector.userReactedTo?.id == responseVector.channel.client.selfId && responseVector.message.content.contains("ᶠᵉᵉᵈ ᵐᵉ")
    }
}
