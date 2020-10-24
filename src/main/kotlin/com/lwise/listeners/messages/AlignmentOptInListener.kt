package com.lwise.listeners.messages

import com.lwise.alignment.AlignmentDefinitions.Companion.ALIGNMENT_ROLES
import com.lwise.types.MessageEvent
import com.lwise.util.log
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class AlignmentOptInListener : MessageListener {
    override val regexString: String = "meow!play alignment"

    override fun isTriggered(content: String): Boolean {
        return content.equals(regexString, ignoreCase = true)
    }

    override fun getResponseMessage() = "<:hello:698742819933913139> welcome to the alignment game~!"
    private fun getFailureResponseMessage() = "it looks like you are already participating in the alignment game <:yespls:698742820273651773>"

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val userToOptIn = responseVector.author
        val userAlignmentRoles = userToOptIn.roles
            .map { role ->
                log(this.javaClass.name, role.name)
                role.name
            }.filter { roleName ->
                ALIGNMENT_ROLES.contains(roleName)
            }.collectList()
        val filteredRoles = userAlignmentRoles.block()!!
        return if (!filteredRoles.isNullOrEmpty()) {
            responseVector.channel.createMessage(getFailureResponseMessage())
        } else {
            super.respond(responseVector)
        }
    }
}
