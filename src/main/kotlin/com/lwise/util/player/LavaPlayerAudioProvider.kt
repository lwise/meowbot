package com.lwise.util.player

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import discord4j.voice.AudioProvider
import java.nio.ByteBuffer

class LavaPlayerAudioProvider(private val player: AudioPlayer) : AudioProvider(
    ByteBuffer.allocate(
        StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()
    )
) {
    private val frame: MutableAudioFrame = MutableAudioFrame()

    init {
        frame.setBuffer(buffer)
    }

    override fun provide(): Boolean {
        // AudioPlayer writes audio data to its AudioFrame
        val didProvide = player.provide(frame)
        // If audio was provided, flip from write mode to read mode
        if (didProvide) buffer.flip()
        return didProvide
    }
}
