package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono
import java.util.Collections

class SecretSantaListener : MessageListener {

    override val regexString = "m!secretsanta*"

    override fun getResponseMessage() = "HoHoHo~~ Secret Santa let's go!! " +
        "Wait for my message for your destined one meow~!"

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val message = responseVector.message.content

        val users = message.split(" ").filter { it.contains("<") && it.contains(">") }

        val participantIds = users.mapNotNull { Snowflake.of("\\d+".toRegex().find(it)?.value!!) }

        val senders = participantIds.shuffled()
        val receivers = senders.toMutableList()
        Collections.rotate(receivers, 1)

        for (i in 0..senders.size - 1) {
            val receiver = responseVector.guild.getMemberById(receivers.get(i)).block()!!
            val sender = responseVector.guild.getMemberById(senders.get(i)).block()!!

            sender.privateChannel.subscribe {
                it.createMessage(
                    "Miao!!  " + " /ᐠ｡ﻌ｡ᐟ\\\\" + "  !! Human Secret Santa: " + sender.displayName + ",\n" +
                        "The destined person that you MUST SEND A GIFT TO is " + receiver.displayName + " !!" +
                        "\n\n ......DO IT OR ELSE!!!!!" + "  \\\\/ᐠ-ᆽ-ᐟ \\\\ "
                )
                    .subscribe()
            }
        }

        responseVector.channel.createMessage(getResponseMessage())
        return super.respond(responseVector)
    }
}
