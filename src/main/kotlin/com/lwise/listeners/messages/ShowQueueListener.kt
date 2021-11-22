package com.lwise.listeners.messages

import com.lwise.types.events.MessageEvent
import com.lwise.util.ConfigUtil
import com.lwise.util.player.MusicPlayer
import discord4j.core.`object`.entity.Message
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import reactor.core.publisher.Mono
import java.util.function.Consumer

class ShowQueueListener : MessageListener {
    companion object {
        private const val MAX_SONG_DISPLAY_SIZE = 10
    }

    override val regexString: String
        get() = "m!queue"

    override fun getResponseMessage(): String = ""

    override fun respond(responseVector: MessageEvent): Mono<Message> {
        val nowPlayingString = MusicPlayer.getNowPlayingAsString()
        if (MusicPlayer.isEmpty && nowPlayingString.isNullOrEmpty()) {
            return responseVector.channel.createMessage("You need to queue some songs first! /ᐠ. ⱉ .ᐟ\\\\ﾉ Use `m!queue <url>`")
        }
        // Can add paging functionality later
        val queueList = MusicPlayer.getQueueDataAsStringList(0, MAX_SONG_DISPLAY_SIZE)
        val queueListString = queueList.joinToString("\n")
        val consumer = Consumer<EmbedCreateSpec> { embedSpec ->
            embedSpec.apply {
                setColor(Color.DARK_GOLDENROD)
                setTitle("Music Queue ${ConfigUtil.emoji["happyCat"]}")
                setFooter("Displaying first ${queueList.size}/${MusicPlayer.queueSize()} songs:", "https://uboachan.net/warc/src/1329727415262.gif")
                nowPlayingString?.let {
                    addField("Now Playing", it, false)
                }
                setDescription(queueListString)
            }
        }

        return responseVector.channel.createEmbed(consumer)
    }
}
