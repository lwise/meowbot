package com.lwise.listeners.reactions

import com.lwise.types.events.ReactionEvent
import com.lwise.util.DatabaseClient
import com.lwise.util.UserDataTransformer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class FishReactionListener : ReactionListener {
    override fun isTriggered(content: String): Boolean {
        return content == "fish"
    }

    override fun respond(responseVector: ReactionEvent): Mono<Message> {
        if (wasAMeowbotFeedingRequest(responseVector)) {
            val userToGiveFishPointsTo = responseVector.reactingUser
            val userFetchQuery = "SELECT * FROM users WHERE username = '${userToGiveFishPointsTo.username}';"
            val userFromDatabase = DatabaseClient.query(userFetchQuery, UserDataTransformer())
            userFromDatabase?.let { user ->
                val newPoints = user.fishPoints + 1
                val userUpdateQuery = "UPDATE users SET fish_points = $newPoints WHERE id = '${userToGiveFishPointsTo.id}';"
                DatabaseClient.update(userUpdateQuery)
            }
        }
        return Mono.empty()
    }

    private fun wasAMeowbotFeedingRequest(responseVector: ReactionEvent): Boolean {
        return responseVector.userReactedTo?.id == responseVector.channel.client.selfId && responseVector.message.content.contains("ᶠᵉᵉᵈ ᵐᵉ")
    }
}
