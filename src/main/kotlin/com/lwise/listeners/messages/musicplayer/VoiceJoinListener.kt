package com.lwise.listeners.messages.musicplayer

import com.lwise.listeners.messages.MessageListener
import com.lwise.player.MusicPlayer
import com.lwise.types.events.MessageEvent
import discord4j.core.`object`.entity.Message
import discord4j.core.spec.VoiceChannelJoinSpec
import reactor.core.publisher.Mono

class VoiceJoinListener : MessageListener {
    override val regexString: String
        get() = "m!join"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val requestingUser = responseVector.author
        val voiceJoinSpec = VoiceChannelJoinSpec.builder().apply {
            provider(MusicPlayer.audioProvider)
        }.build()
        requestingUser?.let { user ->
            user.voiceState.block()?.let { voiceState ->
                voiceState.channel.block()?.join(voiceJoinSpec)?.block()
            }
        }
        return Mono.empty()
    }
}
