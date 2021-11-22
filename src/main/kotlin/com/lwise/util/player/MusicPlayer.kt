package com.lwise.util.player

import com.lwise.util.safeSubList
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer
import discord4j.voice.AudioProvider

object MusicPlayer {
    private val player: AudioPlayer
    private val playerManager: AudioPlayerManager
    private val trackScheduler: TrackScheduler
    val audioProvider: AudioProvider
    val isEmpty: Boolean
        get() = trackScheduler.isEmpty

    init {
        playerManager = DefaultAudioPlayerManager()
        playerManager.configuration.setFrameBufferFactory(::NonAllocatingAudioFrameBuffer)
        AudioSourceManagers.registerRemoteSources(playerManager)
        AudioSourceManagers.registerLocalSource(playerManager)
        player = playerManager.createPlayer()
        audioProvider = LavaPlayerAudioProvider(player)
        trackScheduler = TrackScheduler(player)
        player.addListener(trackScheduler)
    }

    fun addTrackToQueue(url: String) {
        playerManager.loadItemOrdered(this, url, ResultHandler())
    }

    fun removeTrackFromQueue(index: Int): String? {
        return trackScheduler.removeFromQueue(index)?.toDescriptionString()
    }

    fun getQueueDataAsStringList(fromIndex: Int, toIndex: Int): List<String> {
        return trackScheduler.getQueueTrackList().safeSubList(fromIndex, toIndex).mapIndexed { index, track ->
            track.toStringWithIndex(index)
        }
    }

    fun getNowPlayingAsString(): String? {
        return player.playingTrack?.toDescriptionString()
    }

    fun clearQueue() {
        trackScheduler.clearQueue()
    }

    fun pause() {
        player.isPaused = true
    }

    fun resume() {
        player.isPaused = false
    }

    fun skip() {
        trackScheduler.nextTrack()
    }

    private fun AudioTrack.toStringWithIndex(index: Int): String {
        return "${index + 1}. ${toDescriptionString()}"
    }

    private fun AudioTrack.toDescriptionString(): String {
        return info.title
    }

    private fun play(track: AudioTrack) {
        trackScheduler.queue(track)
    }

    class ResultHandler : AudioLoadResultHandler {
        override fun trackLoaded(track: AudioTrack?) {
            track?.let { play(it) }
        }

        override fun playlistLoaded(playlist: AudioPlaylist?) {
            playlist?.let {
                if (it.isSearchResult) {
                    play(it.tracks.first())
                } else {
                    it.tracks.forEach { track ->
                        play(track)
                    }
                }
            }
        }

        override fun noMatches() {
            // MeowBot can handle this later
        }

        override fun loadFailed(exception: FriendlyException?) {
            // MeowBot can handle this later
        }
    }
}