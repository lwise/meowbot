package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.ConfigUtil
import com.lwise.util.DatabaseClient
import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class AlignmentOptInListener : MessageListener {

    override val regexString: String = "m!play alignment"

    override fun isTriggered(content: String): Boolean {
        return content.equals(regexString, ignoreCase = true)
    }

    override fun getResponseMessage() = "${ConfigUtil.emoji["hello"]} welcome to the alignment game~!"
    private fun getFailureResponseMessage() = "it looks like you are already participating in the alignment game ${ConfigUtil.emoji["happyCat"]}"

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val userToOptIn = responseVector.author
        val userAlignmentRoles = userToOptIn.roles
            .map { role ->
                role.name
            }.filter { roleName ->
                ConfigUtil.alignmentRoles.keys.contains(roleName)
            }.collectList()
        val filteredRoles = userAlignmentRoles.block()!!
        return if (!filteredRoles.isNullOrEmpty()) {
            // the user is already playing
            responseVector.channel.createMessage(getFailureResponseMessage())
        } else {
            // need to set the user up

            // insert user into database
            val userId = userToOptIn.id.asBigInteger()
            val userName = userToOptIn.username
            val userQuery = "INSERT INTO users (id, username, chaotic_points, lawful_points, good_points, evil_points) VALUES ($userId, '$userName', 0, 0, 0, 0) ON CONFLICT DO NOTHING;"
            DatabaseClient.update(userQuery)

            // give the user the True Neutral role to start
            userToOptIn.addRole(Snowflake.of(ConfigUtil.alignmentRoles["True Neutral"])).block()
            super.respond(responseVector)
        }
    }
}
