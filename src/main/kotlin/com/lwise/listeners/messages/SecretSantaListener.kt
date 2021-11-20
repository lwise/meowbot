package com.lwise.listeners.messages

import com.lwise.MeowBot
import com.lwise.types.events.MessageEvent
import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.Collections
import java.util.Optional

class SecretSantaListener : MessageListener {

    override val regexString = "m!secretsanta*"

    override fun getResponseMessage() = "HoHoHo~~ Secret Santa let's go!! " +
        "Wait for my message for your destined one meow~!"

    fun getUserNameFromId(id: Snowflake, guildId: Snowflake): Optional<String> {
        return MeowBot.client.getMemberById(guildId, id)
            .blockOptional(Duration.ofMinutes(1))
            .get().nickname
    }

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val message = responseVector.message.content
        val guildId = responseVector.guild.id

        val users = message.split(" ").filter { it.contains("<") && it.contains(">") }
        users.forEach { System.out.println(it) }

        val participantIds = users.mapNotNull { Snowflake.of("\\d+".toRegex().find(it)?.value) }

        val senders = participantIds.shuffled()
        val receivers = senders.toMutableList()
        Collections.rotate(receivers, 1)

        for (i in 0..senders.size - 1) {
            val receiverNickName = getUserNameFromId(receivers.get(i), guildId).orElse("NULL")
            val senderNickName = getUserNameFromId(senders.get(i), guildId).orElse("NULL")

            MeowBot.client.getMemberById(guildId, senders.get(i)).subscribe {
                it.privateChannel.subscribe {
                    it
                        .createMessage(
                            "Miao!!  " +  " /ᐠ｡ﻌ｡ᐟ\\\\" + "  !! Human Secret Santa: " + senderNickName + ",\n" +
                                "The destined person that you MUST SEND A GIFT TO is " + receiverNickName + " !!" +
                                "\n\n ......DO IT OR ELSE!!!!!" +  "  \\\\/ᐠ-ᆽ-ᐟ \\\\ "
                        )
                        .subscribe()
                }
            }
        }

        responseVector.channel.createMessage(getResponseMessage())
        return super.respond(responseVector)
    }
}
