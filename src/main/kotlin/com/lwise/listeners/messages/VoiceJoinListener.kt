package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class VoiceJoinListener : MessageListener {
    override val regexString: String
        get() = "m!join"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val requestingUser = responseVector.author
        requestingUser?.let { user ->
            user.voiceState.block()?.let { voiceState ->
                voiceState.channel.block()?.join { spec ->
                    spec.setProvider(MusicPlayer.audioProvider)
                }?.block()
            }
        }
        return Mono.empty()
    }
}
