package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

class RemoveFromQueueListener : MessageListener {
    override val regexString: String
        get() = "m!remove\\s.*"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val index = responseVector.message.content.split(" ")[1].toIntOrNull()
        index?.let { displayIndex ->
            // The queue is displayed with the queue starting at 1, so we have to subtract 1
            val realIndex = displayIndex - 1
            val removedTrackDescription = MusicPlayer.removeTrackFromQueue(realIndex)
            removedTrackDescription?.let {
                return responseVector.channel.createMessage("Removed $it!")
            }
        }
        return responseVector.channel.createMessage("Hmmm.... I couldn't remove that...")
    }
}
